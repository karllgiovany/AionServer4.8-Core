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
package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.BannedMacManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author KID
 */
public class UnBanMac extends AdminCommand {

    public UnBanMac() {
        super("unbanmac");
    }

    private static final Logger log = LoggerFactory.getLogger("GM_MONITOR_LOG");

    @Override
    public void execute(Player player, String... params) {
        if (params == null || params.length < 1) {
            onFail(player, null);
            return;
        }

        String address = params[0];
        boolean result = BannedMacManager.getInstance().unbanAddress(address,
                "uban;mac=" + address + ", " + player.getObjectId() + "; admin=" + player.getName());
        if (result) {
            PacketSendUtility.sendMessage(player, "mac " + address + " has unbanned");
            log.info("[unbanmac] GM : " + player.getName() + " has unbanned mac [" + address + "] in mapId '" + player.getWorldId() + "'");
        } else {
            PacketSendUtility.sendMessage(player, "mac " + address + " is not banned");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: //unbanmac <mac>");
    }
}
