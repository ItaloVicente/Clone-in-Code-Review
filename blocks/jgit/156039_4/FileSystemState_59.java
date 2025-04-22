package org.eclipse.jgit.niofs.fs;

public class AmbiguousFileSystemNameException extends RuntimeException {

	public AmbiguousFileSystemNameException() {
	}

	public AmbiguousFileSystemNameException(String msg) {
		super(msg);
	}
}
