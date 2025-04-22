
package org.eclipse.jgit.notes;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class Note extends ObjectId {
	private ObjectId data;

	Note(AnyObjectId noteOn
		super(noteOn);
		data = noteData;
	}

	ObjectId getData() {
		return data;
	}

	void setData(ObjectId newData) {
		data = newData;
	}

	@Override
	public String toString() {
		return "Note[" + name() + " -> " + data.name() + "]";
	}
}
