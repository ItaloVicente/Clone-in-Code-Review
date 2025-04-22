
package org.eclipse.jgit.util.fs;

public class DirEnt {
	public static final int TYPE_UNKNOWN = 0;

	public static final int TYPE_DIRECTORY = 1;

	public static final int TYPE_FILE = 2;

	public static final int TYPE_SYMLINK = 3;

	private String name;

	private int type;

	public DirEnt(String name) {
		this(name
	}

	public DirEnt(String name
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return getName();
	}
}
