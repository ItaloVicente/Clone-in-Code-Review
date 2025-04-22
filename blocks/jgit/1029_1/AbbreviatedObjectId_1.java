
package org.eclipse.jgit.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;

public class RenameDetector {

	private static final Comparator<DiffEntry> DIFF_COMPARATOR = new Comparator<DiffEntry>() {
		public int compare(DiffEntry o1
			return o1.newName.compareTo(o2.newName);
		}
	};

	private final Repository repo;

	private final List<DiffEntry> entries = new ArrayList<DiffEntry>();

	private List<DiffEntry> deleted = new ArrayList<DiffEntry>();

	private List<DiffEntry> added = new ArrayList<DiffEntry>();

	private boolean done = false;

	public RenameDetector(Repository repo) {
		this.repo = repo;
	}

	public void addTreeWalk(TreeWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (done)
			throw new IllegalStateException("Renames have already been found");
		while (walk.next()) {
			DiffEntry entry = new DiffEntry();
			entry.oldId = walk.getObjectId(0).abbreviate(repo);
			entry.newId = walk.getObjectId(1).abbreviate(repo);
			entry.oldMode = walk.getFileMode(0);
			entry.newMode = walk.getFileMode(1);
			entry.newName = entry.oldName = walk.getPathString();
			if (entry.oldId.equals(ObjectId.zeroId())) {
				entry.changeType = ChangeType.ADD;
				added.add(entry);
			} else if (entry.newId.equals(ObjectId.zeroId())) {
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
			throw new IllegalStateException("Renames have already been found");
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
			findRenames();
			done = true;
			Collections.sort(entries
		}
		return Collections.unmodifiableList(entries);
	}

	@SuppressWarnings("unchecked")
	private void findRenames() {
		HashMap<ObjectId

		for (DiffEntry del : deleted) {
			ObjectId id = del.oldId.toObjectId();
			Object old = map.put(id
			if (old != null) {
				if (old instanceof DiffEntry) {
					map.put(id
				} else {
					((List) old).add(del);
					map.put(id
				}
			}
		}
		deleted = null;

		ArrayList<DiffEntry> tempAdded = new ArrayList<DiffEntry>(added.size());

		for (DiffEntry add : added) {
			ObjectId id = add.newId.toObjectId();
			Object del = map.remove(id);
			if (del != null) {
				if (del instanceof DiffEntry) {
					entries.add(resolveRename(add
				} else {
					entries.add(resolveRename(add
							.get(0)
					((List<DiffEntry>) del).remove(0);
					map.put(id
				}
			} else {
				tempAdded.add(add);
			}
		}
		entries.addAll(tempAdded);
		added = null;

		for (Object o : map.values()) {
			if (o instanceof DiffEntry)
				entries.add((DiffEntry) o);
			else
				entries.addAll((List<DiffEntry>) o);
		}
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
