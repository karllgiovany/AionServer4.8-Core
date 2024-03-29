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
package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;

/**
 * In this packet Gameserver is asking if given account sessionKey is valid at
 * Loginserver side. [if user that is authenticating on Gameserver is already
 * authenticated on Loginserver]
 *

 */
public class SM_ACCOUNT_AUTH extends LsServerPacket {

    /**
     * accountId [part of session key]
     */
    private final int accountId;
    /**
     * loginOk [part of session key]
     */
    private final int loginOk;
    /**
     * playOk1 [part of session key]
     */
    private final int playOk1;
    /**
     * playOk2 [part of session key]
     */
    private final int playOk2;

    /**
     * Constructs new instance of <tt>SM_ACCOUNT_AUTH </tt> packet.
     *
     * @param accountId account identifier.
     * @param loginOk
     * @param playOk1
     * @param playOk2
     */
    public SM_ACCOUNT_AUTH(int accountId, int loginOk, int playOk1, int playOk2) {
        super(0x01);
        this.accountId = accountId;
        this.loginOk = loginOk;
        this.playOk1 = playOk1;
        this.playOk2 = playOk2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(LoginServerConnection con) {
        writeD(accountId);
        writeD(loginOk);
        writeD(playOk1);
        writeD(playOk2);
    }
}
