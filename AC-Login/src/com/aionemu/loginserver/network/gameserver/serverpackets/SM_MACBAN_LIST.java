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
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.loginserver.network.gameserver.serverpackets;

import com.aionemu.loginserver.controller.BannedMacManager;
import com.aionemu.loginserver.model.base.BannedMacEntry;
import com.aionemu.loginserver.network.gameserver.GsConnection;
import com.aionemu.loginserver.network.gameserver.GsServerPacket;

import java.util.Map;

/**
 * @author KID
 */
public class SM_MACBAN_LIST extends GsServerPacket {

    private Map<String, BannedMacEntry> bannedList;

    public SM_MACBAN_LIST() {
        this.bannedList = BannedMacManager.getInstance().getMap();
    }

    @Override
    protected void writeImpl(GsConnection con) {
        writeC(9);
        writeD(bannedList.size());

        for (BannedMacEntry entry : bannedList.values()) {
            writeS(entry.getMac());
            writeQ(entry.getTime().getTime());
            writeS(entry.getDetails());
        }
    }
}
