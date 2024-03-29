/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 

 * @-Aion-Lightning
 * @Goong_ADM

 

 */
package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.stats.container.PlayerLifeStats;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author Sarynth (Thx Rhys2002 for Packets)
 */
public class SM_ALLIANCE_MEMBER_INFO extends AionServerPacket {

    private Player player;
    private PlayerAllianceEvent event;
    private final int allianceId;
    private final int objectId;

    public SM_ALLIANCE_MEMBER_INFO(PlayerAllianceMember member, PlayerAllianceEvent event) {
        this.player = member.getObject();
        this.event = event;
        this.allianceId = member.getAllianceId();
        this.objectId = member.getObjectId();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        PlayerCommonData pcd = player.getCommonData();
        WorldPosition wp = player.getPosition();

        /**
         * Required so that when member is disconnected, and his
         * playerAllianceGroup slot is changed, he will continue to appear as
         * disconnected to the alliance.
         */
        if (event == PlayerAllianceEvent.ENTER && !player.isOnline()) {
            event = PlayerAllianceEvent.ENTER_OFFLINE;
        }

        writeD(allianceId);
        writeD(objectId);
        if (player.isOnline()) {
            PlayerLifeStats pls = player.getLifeStats();
            writeD(pls.getMaxHp());
            writeD(pls.getCurrentHp());
            writeD(pls.getMaxMp());
            writeD(pls.getCurrentMp());
            writeD(pls.getMaxFp());
            writeD(pls.getCurrentFp());
        } else {
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
        }

        writeD(0);//unk 3.5
        writeD(wp.getMapId());
        writeD(wp.getMapId());
        writeF(wp.getX());
        writeF(wp.getY());
        writeF(wp.getZ());
        writeC(pcd.getPlayerClass().getClassId());
        writeC(pcd.getGender().getGenderId());
        writeC(pcd.getLevel());
        writeC(this.event.getId());
        writeH(0x00); // channel 0x01?
        writeC(0x0);
        switch (this.event) {
            case LEAVE:
            case LEAVE_TIMEOUT:
            case BANNED:
            case MOVEMENT:
            case DISCONNECTED:
                break;

            case JOIN:
            case ENTER:
            case ENTER_OFFLINE:
            case UPDATE:
            case RECONNECT:
            case APPOINT_VICE_CAPTAIN: // Unused maybe...
            case DEMOTE_VICE_CAPTAIN:
            case APPOINT_CAPTAIN:
                writeS(pcd.getName());
                writeD(0x00); // unk
                writeD(0x00); // unk
                if (player.isOnline()) {
                    writeC(0x7F); // 4.5 slots ?
                    List<Effect> abnormalEffects = player.getEffectController().getAbnormalEffects();
                    writeH(abnormalEffects.size());
                    for (Effect effect : abnormalEffects) {
                        writeD(effect.getEffectorId());
                        writeH(effect.getSkillId());
                        writeC(effect.getSkillLevel());
                        writeC(effect.getTargetSlot());
                        writeD(effect.getRemainingTime());
                    }
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                    writeD(0x00); // unk 4.5
                } else {
                    writeH(0);
                }
                break;
            case MEMBER_GROUP_CHANGE:
                writeS(pcd.getName());
                break;
            default:
                break;
        }
    }
}
