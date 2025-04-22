
package org.eclipse.jgit.transport;

import java.io.IOException;

public class UploadPackInternalServerErrorException extends IOException {
	private static final long serialVersionUID = 1L;

	public UploadPackInternalServerErrorException(Throwable why) {
		initCause(why);
	}
}
