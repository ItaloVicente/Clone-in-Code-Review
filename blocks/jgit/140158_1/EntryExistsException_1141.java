
package org.eclipse.jgit.errors;

public class DirCacheNameConflictException extends IllegalStateException {
	private static final long serialVersionUID = 1L;

	private final String path1;
	private final String path2;

	public DirCacheNameConflictException(String path1
		super(path1 + ' ' + path2);
		this.path1 = path1;
		this.path2 = path2;
	}

	public String getPath1() {
		return path1;
	}

	public String getPath2() {
		return path2;
	}
}
