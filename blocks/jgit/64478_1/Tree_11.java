
package org.eclipse.jgit.lib;

@Deprecated
public class SymlinkTreeEntry extends TreeEntry {

	public SymlinkTreeEntry(final Tree parent
			final byte[] nameUTF8) {
		super(parent
	}

	public FileMode getMode() {
		return FileMode.SYMLINK;
	}

	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(getId()));
		r.append(getFullName());
		return r.toString();
	}
}
