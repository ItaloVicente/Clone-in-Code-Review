
package org.eclipse.jgit.lib;

@Deprecated
public class GitlinkTreeEntry extends TreeEntry {

	public GitlinkTreeEntry(final Tree parent
			final byte[] nameUTF8) {
		super(parent
	}

	public FileMode getMode() {
		return FileMode.GITLINK;
	}

	@Override
	public String toString() {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(getId()));
		r.append(getFullName());
		return r.toString();
	}
}
