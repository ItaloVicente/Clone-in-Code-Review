
package org.eclipse.jgit.transport.aws;

import java.util.Locale;

public class BinaryUtils {

    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i]);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return sb.toString().toLowerCase(Locale.getDefault());
    }

}
