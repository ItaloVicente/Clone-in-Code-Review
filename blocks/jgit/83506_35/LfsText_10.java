package org.eclipse.jgit.lfs.errors;

import java.io.IOException;

public class LfsConfigInvalidException extends IOException {
	private static final long serialVersionUID = 1L;

	public LfsConfigInvalidException(String msg) {
		super(msg);
	}

}
