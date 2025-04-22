
package org.eclipse.jgit.notes;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.TreeFormatter;

class NonNoteEntry extends ObjectId {
	private final byte[] name;

	private final FileMode mode;

	NonNoteEntry next;

	NonNoteEntry(byte[] name
		super(id);
		this.name = name;
		this.mode = mode;
	}

	void format(TreeFormatter fmt) {
		fmt.append(name
	}
}
