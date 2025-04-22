
package org.eclipse.jgit.notes;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;

class LeafBucket extends InMemoryNoteBucket {
	private Note[] notes;

	private int cnt;

	LeafBucket(int prefixLen) {
		super(prefixLen);
		notes = new Note[4];
	}

	private int search(AnyObjectId objId) {
		int low = 0;
		int high = cnt;
		while (low < high) {
			int mid = (low + high) >>> 1;
			int cmp = objId.compareTo(notes[mid]);
			if (cmp < 0)
				high = mid;
			else if (cmp == 0)
				return mid;
			else
				low = mid + 1;
		}
		return -(low + 1);
	}

	ObjectId get(AnyObjectId objId
		int idx = search(objId);
		return 0 <= idx ? notes[idx].getData() : null;
	}

	void parseOneEntry(AnyObjectId noteOn
		growIfFull();
		notes[cnt++] = new Note(noteOn
	}

	private void growIfFull() {
		if (notes.length == cnt) {
			Note[] n = new Note[notes.length * 2];
			System.arraycopy(notes
			notes = n;
		}
	}
}
