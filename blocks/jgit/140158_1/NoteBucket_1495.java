
package org.eclipse.jgit.notes;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

public class Note extends ObjectId {
	private ObjectId data;

	public Note(AnyObjectId noteOn
		super(noteOn);
		data = noteData;
	}

	public ObjectId getData() {
		return data;
	}

	void setData(ObjectId newData) {
		data = newData;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "Note[" + name() + " -> " + data.name() + "]";
	}
}
