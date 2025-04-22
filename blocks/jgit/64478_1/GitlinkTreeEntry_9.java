
package org.eclipse.jgit.lib;

import java.io.IOException;

@Deprecated
public class FileTreeEntry extends TreeEntry {
	private FileMode mode;

	public FileTreeEntry(final Tree parent
			final byte[] nameUTF8
		super(parent
		setExecutable(execute);
	}

	public FileMode getMode() {
		return mode;
	}

	public boolean isExecutable() {
		return getMode().equals(FileMode.EXECUTABLE_FILE);
	}

	public void setExecutable(final boolean execute) {
		mode = execute ? FileMode.EXECUTABLE_FILE : FileMode.REGULAR_FILE;
	}

	public ObjectLoader openReader() throws IOException {
		return getRepository().open(getId()
	}

	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(getId()));
		r.append(' ');
		r.append(isExecutable() ? 'X' : 'F');
		r.append(' ');
		r.append(getFullName());
		return r.toString();
	}
}
