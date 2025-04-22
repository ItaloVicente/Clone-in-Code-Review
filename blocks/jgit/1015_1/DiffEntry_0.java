
package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;

public class DiffEntry {

	public static enum ChangeType {
		ADD

		MODIFY

		DELETE

		RENAME

		COPY;
	}

	protected String oldName;

	protected String newName;

	protected FileMode oldMode;

	protected FileMode newMode;

	protected ChangeType changeType;

	protected int score;

	protected AbbreviatedObjectId oldId;

	protected AbbreviatedObjectId newId;

	public String getOldName() {
		return oldName;
	}

	public String getNewName() {
		return newName;
	}

	public FileMode getOldMode() {
		return oldMode;
	}

	public FileMode getNewMode() {
		return newMode;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public int getScore() {
		return score;
	}

	public AbbreviatedObjectId getOldId() {
		return oldId;
	}

	public AbbreviatedObjectId getNewId() {
		return newId;
	}

}
