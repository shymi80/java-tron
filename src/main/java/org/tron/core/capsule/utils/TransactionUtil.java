/*
 * java-tron is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java-tron is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tron.core.capsule.utils;

import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.utils.ByteUtil;
import org.tron.core.Wallet;
import org.tron.core.capsule.TransactionCapsule;
import org.tron.protos.Contract.TransferContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract;

@Slf4j
public class TransactionUtil {

  public static Transaction newGenesisTransaction(byte[] key, long value)
      throws IllegalArgumentException {

    if (!Wallet.addressValid(key)) {
      throw new IllegalArgumentException("Invalidate address");
    }
    TransferContract transferContract = TransferContract.newBuilder()
        .setAmount(value)
        .setOwnerAddress(ByteString.copyFrom("0x000000000000000000000".getBytes()))
        .setToAddress(ByteString.copyFrom(key))
        .build();

    return new TransactionCapsule(transferContract,
        Contract.ContractType.TransferContract).getInstance();
  }

  /**
   * checkBalance.
   */
  private static boolean checkBalance(long totalBalance, long totalSpent) {
    return totalBalance == totalSpent;
  }

  public static boolean validAccountName(byte[] accountName) {
    if (ByteUtil.isNullOrZeroArray(accountName)) {
      return false;
    }
    if (accountName.length > 32) {
      return false;
    }
    // b must read able.
    for (byte b : accountName) {
      if (b < 0x21) {
        return false; // 0x21 = '!'
      }
      if (b > 0x7E) {
        return false; // 0x7E = '~'
      }
    }
    return true;
  }
  /**
   * Get sender.
   */
 /* public static byte[] getSender(Transaction tx) {
    byte[] pubKey = tx.getRawData().getVin(0).getRawData().getPubKey().toByteArray();
    return ECKey.computeAddress(pubKey);
  } */

}
