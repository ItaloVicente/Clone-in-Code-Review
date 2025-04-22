package org.eclipse.egit.ui.internal.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.ui.UIUtils;
import org.eclipse.egit.ui.internal.DecorationOverlayDescriptor;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilterMarker;
import org.eclipse.ui.model.WorkbenchAdapter;

public class FileDiff extends WorkbenchAdapter {

	public static final Comparator<FileDiff> PATH_COMPARATOR = new Comparator<FileDiff>() {
		@Override
		public int compare(FileDiff o1, FileDiff o2) {
			return o1.getPath().compareTo(o2.getPath());
		}
	};

	private final RevCommit commit;

	private DiffEntry diffEntry;

	static ObjectId[] trees(final RevCommit commit, final RevCommit[] parents) {
		final ObjectId[] r = new ObjectId[parents.length + 1];
		for (int i = 0; i < r.length - 1; i++)
			r[i] = parents[i].getTree().getId();
		r[r.length - 1] = commit.getTree().getId();
		return r;
	}

	public static FileDiff[] compute(final Repository repository,
			final TreeWalk walk, final RevCommit commit,
			final TreeFilter... markTreeFilters) throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {
		return compute(repository, walk, commit, commit.getParents(),
				markTreeFilters);
	}

	public static FileDiff[] compute(final Repository repository,
			final TreeWalk walk, final RevCommit commit,
			final RevCommit[] parents,
			final TreeFilter... markTreeFilters) throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {
		final ArrayList<FileDiff> r = new ArrayList<>();

		if (parents.length > 0) {
			walk.reset(trees(commit, parents));
		} else {
			walk.reset();
			walk.addTree(new EmptyTreeIterator());
			walk.addTree(commit.getTree());
		}

		if (walk.getTreeCount() <= 2) {
			List<DiffEntry> entries = DiffEntry.scan(walk, false, markTreeFilters);
			List<DiffEntry> xentries = new LinkedList<>(entries);
			RenameDetector detector = new RenameDetector(repository);
			detector.addAll(entries);
			List<DiffEntry> renames = detector.compute(walk.getObjectReader(),
					org.eclipse.jgit.lib.NullProgressMonitor.INSTANCE);
			for (DiffEntry m : renames) {
				final FileDiff d = new FileDiff(commit, m);
				r.add(d);
				for (Iterator<DiffEntry> i = xentries.iterator(); i.hasNext();) {
					DiffEntry n = i.next();
					if (m.getOldPath().equals(n.getOldPath()))
						i.remove();
					else if (m.getNewPath().equals(n.getNewPath()))
						i.remove();
				}
			}
			for (DiffEntry m : xentries) {
				final FileDiff d = new FileDiff(commit, m);
				r.add(d);
			}
		}
		else { // DiffEntry does not support walks with more than two trees
			final int nTree = walk.getTreeCount();
			final int myTree = nTree - 1;

			TreeFilterMarker treeFilterMarker = new TreeFilterMarker(
					markTreeFilters);

			while (walk.next()) {
				if (matchAnyParent(walk, myTree))
					continue;

				int treeFilterMarks = treeFilterMarker.getMarks(walk);

				final FileDiffForMerges d = new FileDiffForMerges(commit,
						treeFilterMarks);
				d.path = walk.getPathString();
				int m0 = 0;
				for (int i = 0; i < myTree; i++)
					m0 |= walk.getRawMode(i);
				final int m1 = walk.getRawMode(myTree);
				d.change = ChangeType.MODIFY;
				if (m0 == 0 && m1 != 0)
					d.change = ChangeType.ADD;
				else if (m0 != 0 && m1 == 0)
					d.change = ChangeType.DELETE;
				else if (m0 != m1 && walk.idEqual(0, myTree))
					d.change = ChangeType.MODIFY; // there is no ChangeType.TypeChanged
				d.blobs = new ObjectId[nTree];
				d.modes = new FileMode[nTree];
				for (int i = 0; i < nTree; i++) {
					d.blobs[i] = walk.getObjectId(i);
					d.modes[i] = walk.getFileMode(i);
				}


				r.add(d);
			}

		}

		final FileDiff[] tmp = new FileDiff[r.size()];
		r.toArray(tmp);
		return tmp;
	}

	private static boolean matchAnyParent(final TreeWalk walk, final int myTree) {
		final int m = walk.getRawMode(myTree);
		for (int i = 0; i < myTree; i++)
			if (walk.getRawMode(i) == m && walk.idEqual(i, myTree))
				return true;
		return false;
	}

	public void outputDiff(final StringBuilder d, final Repository db,
			final DiffFormatter diffFmt, boolean gitFormat) throws IOException {
		if (gitFormat) {
			diffFmt.setRepository(db);
			diffFmt.format(diffEntry);
			return;
		}

		try (ObjectReader reader = db.newObjectReader()) {
			outputEclipseDiff(d, db, reader, diffFmt);
		}
	}

	private void outputEclipseDiff(final StringBuilder d, final Repository db,
			final ObjectReader reader, final DiffFormatter diffFmt)
			throws IOException {
		if (!(getBlobs().length == 2))
			throw new UnsupportedOperationException(
					"Not supported yet if the number of parents is different from one"); //$NON-NLS-1$

		String projectRelativeNewPath = getProjectRelativePath(db, getNewPath());
		String projectRelativeOldPath = getProjectRelativePath(db, getOldPath());
		d.append("diff --git ").append(projectRelativeOldPath).append(" ") //$NON-NLS-1$ //$NON-NLS-2$
				.append(projectRelativeNewPath).append("\n"); //$NON-NLS-1$
		final ObjectId id1 = getBlobs()[0];
		final ObjectId id2 = getBlobs()[1];
		final FileMode mode1 = getModes()[0];
		final FileMode mode2 = getModes()[1];

		if (id1.equals(ObjectId.zeroId())) {
			d.append("new file mode " + mode2).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		} else if (id2.equals(ObjectId.zeroId())) {
			d.append("deleted file mode " + mode1).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		} else if (!mode1.equals(mode2)) {
			d.append("old mode " + mode1); //$NON-NLS-1$
			d.append("new mode " + mode2).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		}
		d.append("index ").append(reader.abbreviate(id1).name()). //$NON-NLS-1$
				append("..").append(reader.abbreviate(id2).name()). //$NON-NLS-1$
				append(mode1.equals(mode2) ? " " + mode1 : "").append("\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (id1.equals(ObjectId.zeroId()))
			d.append("--- /dev/null\n"); //$NON-NLS-1$
		else {
			d.append("--- "); //$NON-NLS-1$
			d.append(getProjectRelativePath(db, getOldPath()));
			d.append("\n"); //$NON-NLS-1$
		}

		if (id2.equals(ObjectId.zeroId()))
			d.append("+++ /dev/null\n"); //$NON-NLS-1$
		else {
			d.append("+++ "); //$NON-NLS-1$
			d.append(getProjectRelativePath(db, getNewPath()));
			d.append("\n"); //$NON-NLS-1$
		}

		final RawText a = getRawText(id1, reader);
		final RawText b = getRawText(id2, reader);
		EditList editList = MyersDiff.INSTANCE
				.diff(RawTextComparator.DEFAULT, a, b);
		diffFmt.format(editList, a, b);
	}

	private String getProjectRelativePath(Repository db, String repoPath) {
		IResource resource = ResourceUtil.getFileForLocation(db, repoPath, false);
		if (resource == null)
			return null;
		return resource.getProjectRelativePath().toString();
	}

	private RawText getRawText(ObjectId id, ObjectReader reader)
			throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] {});
		ObjectLoader ldr = reader.open(id, Constants.OBJ_BLOB);
		return new RawText(ldr.getCachedBytes(Integer.MAX_VALUE));
	}

	public RevCommit getCommit() {
		return commit;
	}

	public String getPath() {
		if (ChangeType.DELETE.equals(diffEntry.getChangeType()))
			return diffEntry.getOldPath();
		return diffEntry.getNewPath();
	}

	public String getOldPath() {
		return diffEntry.getOldPath();
	}

	public String getNewPath() {
		return diffEntry.getNewPath();
	}

	public ChangeType getChange() {
		return diffEntry.getChangeType();
	}

	public ObjectId[] getBlobs() {
		List<ObjectId> objectIds = new ArrayList<>();
		if (diffEntry.getOldId() != null)
			objectIds.add(diffEntry.getOldId().toObjectId());
		if (diffEntry.getNewId() != null)
			objectIds.add(diffEntry.getNewId().toObjectId());
		return objectIds.toArray(new ObjectId[]{});
	}

	public FileMode[] getModes() {
		List<FileMode> modes = new ArrayList<>();
		if (diffEntry.getOldMode() != null)
			modes.add(diffEntry.getOldMode());
		if (diffEntry.getOldMode() != null)
			modes.add(diffEntry.getOldMode());
		return modes.toArray(new FileMode[]{});
	}

	public boolean isMarked(int index) {
		return diffEntry != null && diffEntry.isMarked(index);
	}

	public FileDiff(final RevCommit c, final DiffEntry entry) {
		diffEntry = entry;
		commit = c;
	}

	public boolean isSubmodule() {
		if (diffEntry == null)
			return false;
		return diffEntry.getOldMode() == FileMode.GITLINK
				|| diffEntry.getNewMode() == FileMode.GITLINK;
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		final ImageDescriptor base;
		if (!isSubmodule())
			base = UIUtils.getEditorImage(getPath());
		else
			base = UIIcons.REPOSITORY;
		switch (getChange()) {
		case ADD:
			return new DecorationOverlayDescriptor(base,
					UIIcons.OVR_STAGED_ADD, IDecoration.BOTTOM_RIGHT);
		case DELETE:
			return new DecorationOverlayDescriptor(base,
					UIIcons.OVR_STAGED_REMOVE, IDecoration.BOTTOM_RIGHT);
		case RENAME:
			return new DecorationOverlayDescriptor(base,
					UIIcons.OVR_STAGED_RENAME, IDecoration.BOTTOM_RIGHT);
		default:
			return base;
		}
	}

	@Override
	public String getLabel(Object object) {
		return getPath();
	}

	private static class FileDiffForMerges extends FileDiff {
		private String path;

		private ChangeType change;

		private ObjectId[] blobs;

		private FileMode[] modes;

		private final int treeFilterMarks;

		private FileDiffForMerges(final RevCommit c, int treeFilterMarks) {
			super (c, null);
			this.treeFilterMarks = treeFilterMarks;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public String getNewPath() {
			return path;
		}

		@Override
		public ChangeType getChange() {
			return change;
		}

		@Override
		public ObjectId[] getBlobs() {
			return blobs;
		}

		@Override
		public FileMode[] getModes() {
			return modes;
		}

		@Override
		public boolean isMarked(int index) {
			return (treeFilterMarks & (1L << index)) != 0;
		}
	}
}
