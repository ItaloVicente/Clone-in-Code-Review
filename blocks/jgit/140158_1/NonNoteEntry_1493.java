
package org.eclipse.jgit.notes;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_STRING_LENGTH;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectInserter.Formatter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.TreeFormatter;

class LeafBucket extends InMemoryNoteBucket {
	static final int MAX_SIZE = 256;

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

	@Override
	Note getNote(AnyObjectId objId
		int idx = search(objId);
		return 0 <= idx ? notes[idx] : null;
	}

	Note get(int index) {
		return notes[index];
	}

	int size() {
		return cnt;
	}

	@Override
	Iterator<Note> iterator(AnyObjectId objId
		return new Iterator<Note>() {
			private int idx;

			@Override
			public boolean hasNext() {
				return idx < cnt;
			}

			@Override
			public Note next() {
				if (hasNext())
					return notes[idx++];
				else
					throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	int estimateSize(AnyObjectId noteOn
		return cnt;
	}

	@Override
	InMemoryNoteBucket set(AnyObjectId noteOn
			ObjectReader or) throws IOException {
		int p = search(noteOn);
		if (0 <= p) {
			if (noteData != null) {
				notes[p].setData(noteData.copy());
				return this;

			} else {
				System.arraycopy(notes
				cnt--;
				return 0 < cnt ? this : null;
			}

		} else if (noteData != null) {
			if (shouldSplit()) {
				return split().set(noteOn

			} else {
				growIfFull();
				p = -(p + 1);
				if (p < cnt)
					System.arraycopy(notes
				notes[p] = new Note(noteOn
				cnt++;
				return this;
			}

		} else {
			return this;
		}
	}

	@Override
	ObjectId writeTree(ObjectInserter inserter) throws IOException {
		return inserter.insert(build());
	}

	@Override
	ObjectId getTreeId() {
		try (Formatter f = new ObjectInserter.Formatter()) {
			return f.idFor(build());
		}
	}

	private TreeFormatter build() {
		byte[] nameBuf = new byte[OBJECT_ID_STRING_LENGTH];
		int nameLen = OBJECT_ID_STRING_LENGTH - prefixLen;
		TreeFormatter fmt = new TreeFormatter(treeSize(nameLen));
		NonNoteEntry e = nonNotes;

		for (int i = 0; i < cnt; i++) {
			Note n = notes[i];

			n.copyTo(nameBuf

			while (e != null
					&& e.pathCompare(nameBuf
				e.format(fmt);
				e = e.next;
			}

			fmt.append(nameBuf
		}

		for (; e != null; e = e.next)
			e.format(fmt);
		return fmt;
	}

	private int treeSize(int nameLen) {
		int sz = cnt * TreeFormatter.entrySize(REGULAR_FILE
		for (NonNoteEntry e = nonNotes; e != null; e = e.next)
			sz += e.treeEntrySize();
		return sz;
	}

	void parseOneEntry(AnyObjectId noteOn
		growIfFull();
		notes[cnt++] = new Note(noteOn
	}

	@Override
	InMemoryNoteBucket append(Note note) {
		if (shouldSplit()) {
			return split().append(note);

		} else {
			growIfFull();
			notes[cnt++] = note;
			return this;
		}
	}

	private void growIfFull() {
		if (notes.length == cnt) {
			Note[] n = new Note[notes.length * 2];
			System.arraycopy(notes
			notes = n;
		}
	}

	private boolean shouldSplit() {
		return MAX_SIZE <= cnt && prefixLen + 2 < OBJECT_ID_STRING_LENGTH;
	}

	FanoutBucket split() {
		FanoutBucket n = new FanoutBucket(prefixLen);
		for (int i = 0; i < cnt; i++)
			n.append(notes[i]);
		n.nonNotes = nonNotes;
		return n;
	}
}
