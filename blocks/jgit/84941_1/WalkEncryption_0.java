
package org.eclipse.jgit.transport;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

class InsecureCipherFactory {
	static Cipher create(String algo)
			throws NoSuchAlgorithmException
		return Cipher.getInstance(algo);
	}
}
