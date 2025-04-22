
package org.eclipse.jgit.util.fs;

import java.io.IOException;

public class FileExistsException extends IOException {
	private static final long serialVersionUID = 1L;

	public FileExistsException(String file) {
		super(file);
	}
}
