
package org.eclipse.jgit.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;

public class RenameDetector {

	private static final int EXACT_RENAME_SCORE = 100;

	private static final Comparator<DiffEntry> DIFF_COMPARATOR = new Comparator<DiffEntry>() {
		public int compare(DiffEntry o1
			return o1.newName.compareTo(o2.newName);
		}
	};

	private final List<DiffEntry> entries = new ArrayList<DiffEntry>();

	private List<DiffEntry> deleted = new ArrayList<DiffEntry>();

	private List<DiffEntry> added = new ArrayList<DiffEntry>();

	private boolean done = false;

	public void addTreeWalk(TreeWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (done)
			throw new IllegalStateException(JGitText.get().renamesAlreadyFound);
		MutableObjectId idBuf = new MutableObjectId();
		while (walk.next()) {
			DiffEntry entry = new DiffEntry();
			walk.getObjectId(idBuf
			entry.oldId = AbbreviatedObjectId.fromObjectId(idBuf);
			walk.getObjectId(idBuf
			entry.newId = AbbreviatedObjectId.fromObjectId(idBuf);
			entry.oldMode = walk.getFileMode(0);
			entry.newMode = walk.getFileMode(1);
			entry.newName = entry.oldName = walk.getPathString();
			if (entry.oldMode == FileMode.MISSING) {
				entry.changeType = ChangeType.ADD;
				added.add(entry);
			} else if (entry.newMode == FileMode.MISSING) {
				entry.changeType = ChangeType.DELETE;
				deleted.add(entry);
			} else {
				entry.changeType = ChangeType.MODIFY;
				entries.add(entry);
			}
		}
	}

	public void addDiffEntry(DiffEntry entry) {
		if (done)
			throw new IllegalStateException(JGitText.get().renamesAlreadyFound);
		switch (entry.changeType) {
		case ADD:
			added.add(entry);
			break;
		case DELETE:
			deleted.add(entry);
			break;
		case COPY:
		case MODIFY:
		case RENAME:
		default:
			entries.add(entry);
		}
	}

	public List<DiffEntry> getEntries() throws IOException {
		if (!done) {
			done = true;
			findExactRenames();
			entries.addAll(added);
			entries.addAll(deleted);
			added = null;
			deleted = null;
			Collections.sort(entries
		}
		return Collections.unmodifiableList(entries);
	}

	@SuppressWarnings("unchecked")
	private void findExactRenames() {
		HashMap<AbbreviatedObjectId

		for (DiffEntry del : deleted) {
			Object old = map.put(del.oldId
			if (old != null) {
				if (old instanceof DiffEntry) {
					ArrayList<DiffEntry> tmp = new ArrayList<DiffEntry>(2);
					tmp.add((DiffEntry) old);
					tmp.add(del);
					map.put(del.oldId
				} else {
					((List) old).add(del);
					map.put(del.oldId
				}
			}
		}

		ArrayList<DiffEntry> tempAdded = new ArrayList<DiffEntry>(added.size());

		for (DiffEntry add : added) {
			Object del = map.remove(add.newId);
			if (del != null) {
				if (del instanceof DiffEntry) {
					entries.add(resolveRename(add
							EXACT_RENAME_SCORE));
				} else {
					List<DiffEntry> tmp = (List<DiffEntry>) del;
					entries.add(resolveRename(add
							EXACT_RENAME_SCORE));
					if (!tmp.isEmpty())
						map.put(add.newId
				}
			} else {
				tempAdded.add(add);
			}
		}
		added = tempAdded;

		Collection<Object> values = map.values();
		ArrayList<DiffEntry> tempDeleted = new ArrayList<DiffEntry>(values
				.size());
		for (Object o : values) {
			if (o instanceof DiffEntry)
				tempDeleted.add((DiffEntry) o);
			else
				tempDeleted.addAll((List<DiffEntry>) o);
		}
		deleted = tempDeleted;
	}

	private DiffEntry resolveRename(DiffEntry add
		DiffEntry renamed = new DiffEntry();

		renamed.oldId = del.oldId;
		renamed.oldMode = del.oldMode;
		renamed.oldName = del.oldName;
		renamed.newId = add.newId;
		renamed.newMode = add.newMode;
		renamed.newName = add.newName;
		renamed.changeType = ChangeType.RENAME;
		renamed.score = score;

		return renamed;
	}
}
