
package org.eclipse.jgit.notes;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

public class NoteMap implements Iterable<Note> {
	public static NoteMap newEmptyMap() {
		r.root = new LeafBucket(0);
		return r;
	}

	public static String shortenRefName(String noteRefName) {
		if (noteRefName.startsWith(Constants.R_NOTES))
			return noteRefName.substring(Constants.R_NOTES.length());
		return noteRefName;
	}

	public static NoteMap read(ObjectReader reader
			throws MissingObjectException
			CorruptObjectException
		return read(reader
	}

	public static NoteMap read(ObjectReader reader
			throws MissingObjectException
			CorruptObjectException
		return readTree(reader
	}

	public static NoteMap readTree(ObjectReader reader
			throws MissingObjectException
			CorruptObjectException
		NoteMap map = new NoteMap(reader);
		map.load(treeId);
		return map;
	}

	static NoteMap newMap(InMemoryNoteBucket root
		NoteMap map = new NoteMap(reader);
		map.root = root;
		return map;
	}

	private final ObjectReader reader;

	private InMemoryNoteBucket root;

	private NoteMap(ObjectReader reader) {
		this.reader = reader;
	}

	@Override
	public Iterator<Note> iterator() {
		try {
			return root.iterator(new MutableObjectId()
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ObjectId get(AnyObjectId id) throws IOException {
		Note n = root.getNote(id
		return n == null ? null : n.getData();
	}

	public Note getNote(AnyObjectId id) throws IOException {
		return root.getNote(id
	}

	public boolean contains(AnyObjectId id) throws IOException {
		return get(id) != null;
	}

	public byte[] getCachedBytes(AnyObjectId id
			throws LargeObjectException
		ObjectId dataId = get(id);
		if (dataId != null)
			return reader.open(dataId).getCachedBytes(sizeLimit);
		else
			return null;
	}

	public void set(AnyObjectId noteOn
		InMemoryNoteBucket newRoot = root.set(noteOn
		if (newRoot == null) {
			newRoot = new LeafBucket(0);
			newRoot.nonNotes = root.nonNotes;
		}
		root = newRoot;
	}

	public void set(AnyObjectId noteOn
			throws IOException {
		ObjectId dataId;
		if (noteData != null) {
			byte[] dataUTF8 = Constants.encode(noteData);
			dataId = ins.insert(Constants.OBJ_BLOB
		} else {
			dataId = null;
		}
		set(noteOn
	}

	public void remove(AnyObjectId noteOn) throws IOException {
		set(noteOn
	}

	public ObjectId writeTree(ObjectInserter inserter) throws IOException {
		return root.writeTree(inserter);
	}

	InMemoryNoteBucket getRoot() {
		return root;
	}

	private void load(ObjectId rootTree) throws MissingObjectException
			IncorrectObjectTypeException
		root = NoteParser.parse(none
	}
}
