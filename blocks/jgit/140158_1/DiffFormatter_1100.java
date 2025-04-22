
package org.eclipse.jgit.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilterMarker;

public class DiffEntry {
	static final AbbreviatedObjectId A_ZERO = AbbreviatedObjectId
			.fromObjectId(ObjectId.zeroId());


	public static enum ChangeType {
		ADD

		MODIFY

		DELETE

		RENAME

		COPY;
	}

	public static enum Side {
		OLD

		NEW;
	}

	protected DiffEntry(){
	}

	public static List<DiffEntry> scan(TreeWalk walk) throws IOException {
		return scan(walk
	}

	public static List<DiffEntry> scan(TreeWalk walk
			throws IOException {
		return scan(walk
	}

	public static List<DiffEntry> scan(TreeWalk walk
			TreeFilter[] markTreeFilters)
			throws IOException {
		if (walk.getTreeCount() != 2)
			throw new IllegalArgumentException(
					JGitText.get().treeWalkMustHaveExactlyTwoTrees);
		if (includeTrees && walk.isRecursive())
			throw new IllegalArgumentException(
					JGitText.get().cannotBeRecursiveWhenTreesAreIncluded);

		TreeFilterMarker treeFilterMarker;
		if (markTreeFilters != null && markTreeFilters.length > 0)
			treeFilterMarker = new TreeFilterMarker(markTreeFilters);
		else
			treeFilterMarker = null;

		List<DiffEntry> r = new ArrayList<>();
		MutableObjectId idBuf = new MutableObjectId();
		while (walk.next()) {
			DiffEntry entry = new DiffEntry();

			walk.getObjectId(idBuf
			entry.oldId = AbbreviatedObjectId.fromObjectId(idBuf);

			walk.getObjectId(idBuf
			entry.newId = AbbreviatedObjectId.fromObjectId(idBuf);

			entry.oldMode = walk.getFileMode(0);
			entry.newMode = walk.getFileMode(1);
			entry.newPath = entry.oldPath = walk.getPathString();

			if (walk.getAttributesNodeProvider() != null) {
				entry.diffAttribute = walk.getAttributes()
						.get(Constants.ATTR_DIFF);
			}

			if (treeFilterMarker != null)
				entry.treeFilterMarks = treeFilterMarker.getMarks(walk);

			if (entry.oldMode == FileMode.MISSING) {
				entry.oldPath = DiffEntry.DEV_NULL;
				entry.changeType = ChangeType.ADD;
				r.add(entry);

			} else if (entry.newMode == FileMode.MISSING) {
				entry.newPath = DiffEntry.DEV_NULL;
				entry.changeType = ChangeType.DELETE;
				r.add(entry);

			} else if (!entry.oldId.equals(entry.newId)) {
				entry.changeType = ChangeType.MODIFY;
				if (RenameDetector.sameType(entry.oldMode
					r.add(entry);
				else
					r.addAll(breakModify(entry));
			} else if (entry.oldMode != entry.newMode) {
				entry.changeType = ChangeType.MODIFY;
				r.add(entry);
			}

			if (includeTrees && walk.isSubtree())
				walk.enterSubtree();
		}
		return r;
	}

	static DiffEntry add(String path
		DiffEntry e = new DiffEntry();
		e.oldId = A_ZERO;
		e.oldMode = FileMode.MISSING;
		e.oldPath = DEV_NULL;

		e.newId = AbbreviatedObjectId.fromObjectId(id);
		e.newMode = FileMode.REGULAR_FILE;
		e.newPath = path;
		e.changeType = ChangeType.ADD;
		return e;
	}

	static DiffEntry delete(String path
		DiffEntry e = new DiffEntry();
		e.oldId = AbbreviatedObjectId.fromObjectId(id);
		e.oldMode = FileMode.REGULAR_FILE;
		e.oldPath = path;

		e.newId = A_ZERO;
		e.newMode = FileMode.MISSING;
		e.newPath = DEV_NULL;
		e.changeType = ChangeType.DELETE;
		return e;
	}

	static DiffEntry modify(String path) {
		DiffEntry e = new DiffEntry();
		e.oldMode = FileMode.REGULAR_FILE;
		e.oldPath = path;

		e.newMode = FileMode.REGULAR_FILE;
		e.newPath = path;
		e.changeType = ChangeType.MODIFY;
		return e;
	}

	static List<DiffEntry> breakModify(DiffEntry entry) {
		DiffEntry del = new DiffEntry();
		del.oldId = entry.getOldId();
		del.oldMode = entry.getOldMode();
		del.oldPath = entry.getOldPath();

		del.newId = A_ZERO;
		del.newMode = FileMode.MISSING;
		del.newPath = DiffEntry.DEV_NULL;
		del.changeType = ChangeType.DELETE;
		del.diffAttribute = entry.diffAttribute;

		DiffEntry add = new DiffEntry();
		add.oldId = A_ZERO;
		add.oldMode = FileMode.MISSING;
		add.oldPath = DiffEntry.DEV_NULL;

		add.newId = entry.getNewId();
		add.newMode = entry.getNewMode();
		add.newPath = entry.getNewPath();
		add.changeType = ChangeType.ADD;
		add.diffAttribute = entry.diffAttribute;
		return Arrays.asList(del
	}

	static DiffEntry pair(ChangeType changeType
			int score) {
		DiffEntry r = new DiffEntry();

		r.oldId = src.oldId;
		r.oldMode = src.oldMode;
		r.oldPath = src.oldPath;

		r.newId = dst.newId;
		r.newMode = dst.newMode;
		r.newPath = dst.newPath;
		r.diffAttribute = dst.diffAttribute;

		r.changeType = changeType;
		r.score = score;

		r.treeFilterMarks = src.treeFilterMarks | dst.treeFilterMarks;

		return r;
	}

	protected String oldPath;

	protected String newPath;

	protected Attribute diffAttribute;

	protected FileMode oldMode;

	protected FileMode newMode;

	protected ChangeType changeType;

	protected int score;

	protected AbbreviatedObjectId oldId;

	protected AbbreviatedObjectId newId;

	private int treeFilterMarks = 0;

	public String getOldPath() {
		return oldPath;
	}

	public String getNewPath() {
		return newPath;
	}

	public String getPath(Side side) {
		return side == Side.OLD ? getOldPath() : getNewPath();
	}

	public Attribute getDiffAttribute() {
		return diffAttribute;
	}

	public FileMode getOldMode() {
		return oldMode;
	}

	public FileMode getNewMode() {
		return newMode;
	}

	public FileMode getMode(Side side) {
		return side == Side.OLD ? getOldMode() : getNewMode();
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

	public boolean isMarked(int index) {
		return (treeFilterMarks & (1L << index)) != 0;
	}

	public int getTreeFilterMarks() {
		return treeFilterMarks;
	}

	public AbbreviatedObjectId getId(Side side) {
		return side == Side.OLD ? getOldId() : getNewId();
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("DiffEntry[");
		buf.append(changeType);
		buf.append(" ");
		switch (changeType) {
		case ADD:
			buf.append(newPath);
			break;
		case COPY:
			buf.append(oldPath + "->" + newPath);
			break;
		case DELETE:
			buf.append(oldPath);
			break;
		case MODIFY:
			buf.append(oldPath);
			break;
		case RENAME:
			buf.append(oldPath + "->" + newPath);
			break;
		}
		buf.append("]");
		return buf.toString();
	}
}
