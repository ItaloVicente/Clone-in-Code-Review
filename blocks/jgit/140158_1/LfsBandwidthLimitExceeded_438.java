
package org.eclipse.jgit.lfs.errors;

import static java.nio.charset.StandardCharsets.US_ASCII;

import java.text.MessageFormat;

import org.eclipse.jgit.lfs.internal.LfsText;

public class InvalidLongObjectIdException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidLongObjectIdException(byte[] bytes
		super(MessageFormat.format(LfsText.get().invalidLongId
				asAscii(bytes
	}

	public InvalidLongObjectIdException(String idString) {
		super(MessageFormat.format(LfsText.get().invalidLongId
	}

	private static String asAscii(byte[] bytes
		try {
			return new String(bytes
		} catch (StringIndexOutOfBoundsException e) {
		}
	}
}
