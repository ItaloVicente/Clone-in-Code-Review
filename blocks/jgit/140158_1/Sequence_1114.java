
package org.eclipse.jgit.diff;

import static org.eclipse.jgit.diff.DiffEntry.Side.NEW;
import static org.eclipse.jgit.diff.DiffEntry.Side.OLD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.SimilarityIndex.TableFullException;
import org.eclipse.jgit.errors.CancelledException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;

public class RenameDetector {
	private static final int EXACT_RENAME_SCORE = 100;

	private static final Comparator<DiffEntry> DIFF_COMPARATOR = new Comparator<DiffEntry>() {
		@Override
		public int compare(DiffEntry a
			int cmp = nameOf(a).compareTo(nameOf(b));
			if (cmp == 0)
				cmp = sortOf(a.getChangeType()) - sortOf(b.getChangeType());
			return cmp;
		}

		private String nameOf(DiffEntry ent) {
			if (ent.changeType == ChangeType.DELETE)
				return ent.oldPath;
			return ent.newPath;
		}

		private int sortOf(ChangeType changeType) {
			switch (changeType) {
			case DELETE:
				return 1;
			case ADD:
				return 2;
			default:
				return 10;
			}
		}
	};

	private List<DiffEntry> entries;

	private List<DiffEntry> deleted;

	private List<DiffEntry> added;

	private boolean done;

	private final ObjectReader objectReader;

	private int renameScore = 60;

	private int breakScore = -1;

	private int renameLimit;

	private boolean overRenameLimit;

	public RenameDetector(Repository repo) {
		this(repo.newObjectReader()
	}

	public RenameDetector(ObjectReader reader
		objectReader = reader.newReader();
		renameLimit = cfg.getRenameLimit();
		reset();
	}

	public int getRenameScore() {
		return renameScore;
	}

	public void setRenameScore(int score) {
		if (score < 0 || score > 100)
			throw new IllegalArgumentException(
					JGitText.get().similarityScoreMustBeWithinBounds);
		renameScore = score;
	}

	public int getBreakScore() {
		return breakScore;
	}

	public void setBreakScore(int breakScore) {
		this.breakScore = breakScore;
	}

	public int getRenameLimit() {
		return renameLimit;
	}

	public void setRenameLimit(int limit) {
		renameLimit = limit;
	}

	public boolean isOverRenameLimit() {
		if (done)
			return overRenameLimit;
		int cnt = Math.max(added.size()
		return getRenameLimit() != 0 && getRenameLimit() < cnt;
	}

	public void addAll(Collection<DiffEntry> entriesToAdd) {
		if (done)
			throw new IllegalStateException(JGitText.get().renamesAlreadyFound);

		for (DiffEntry entry : entriesToAdd) {
			switch (entry.getChangeType()) {
			case ADD:
				added.add(entry);
				break;

			case DELETE:
				deleted.add(entry);
				break;

			case MODIFY:
				if (sameType(entry.getOldMode()
					entries.add(entry);
				} else {
					List<DiffEntry> tmp = DiffEntry.breakModify(entry);
					deleted.add(tmp.get(0));
					added.add(tmp.get(1));
				}
				break;

			case COPY:
			case RENAME:
			default:
				entries.add(entry);
			}
		}
	}

	public void add(DiffEntry entry) {
		addAll(Collections.singletonList(entry));
	}

	public List<DiffEntry> compute() throws IOException {
		return compute(NullProgressMonitor.INSTANCE);
	}

	public List<DiffEntry> compute(ProgressMonitor pm)
			throws IOException
		if (!done) {
			try {
				return compute(objectReader
			} finally {
				objectReader.close();
			}
		}
		return Collections.unmodifiableList(entries);
	}

	public List<DiffEntry> compute(ObjectReader reader
			throws IOException
		final ContentSource cs = ContentSource.create(reader);
		return compute(new ContentSource.Pair(cs
	}

	public List<DiffEntry> compute(ContentSource.Pair reader
			throws IOException
		if (!done) {
			done = true;

			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;

			if (0 < breakScore)
				breakModifies(reader

			if (!added.isEmpty() && !deleted.isEmpty())
				findExactRenames(pm);

			if (!added.isEmpty() && !deleted.isEmpty())
				findContentRenames(reader

			if (0 < breakScore && !added.isEmpty() && !deleted.isEmpty())
				rejoinModifies(pm);

			entries.addAll(added);
			added = null;

			entries.addAll(deleted);
			deleted = null;

			Collections.sort(entries
		}
		return Collections.unmodifiableList(entries);
	}

	public void reset() {
		entries = new ArrayList<>();
		deleted = new ArrayList<>();
		added = new ArrayList<>();
		done = false;
	}

	private void advanceOrCancel(ProgressMonitor pm) throws CancelledException {
		if (pm.isCancelled()) {
			throw new CancelledException(JGitText.get().renameCancelled);
		}
		pm.update(1);
	}

	private void breakModifies(ContentSource.Pair reader
			throws IOException
		ArrayList<DiffEntry> newEntries = new ArrayList<>(entries.size());

		pm.beginTask(JGitText.get().renamesBreakingModifies

		for (int i = 0; i < entries.size(); i++) {
			DiffEntry e = entries.get(i);
			if (e.getChangeType() == ChangeType.MODIFY) {
				int score = calculateModifyScore(reader
				if (score < breakScore) {
					List<DiffEntry> tmp = DiffEntry.breakModify(e);
					DiffEntry del = tmp.get(0);
					del.score = score;
					deleted.add(del);
					added.add(tmp.get(1));
				} else {
					newEntries.add(e);
				}
			} else {
				newEntries.add(e);
			}
			advanceOrCancel(pm);
		}

		entries = newEntries;
	}

	private void rejoinModifies(ProgressMonitor pm) throws CancelledException {
		HashMap<String
		ArrayList<DiffEntry> newAdded = new ArrayList<>(added.size());

		pm.beginTask(JGitText.get().renamesRejoiningModifies
				+ deleted.size());

		for (DiffEntry src : deleted) {
			nameMap.put(src.oldPath
			advanceOrCancel(pm);
		}

		for (DiffEntry dst : added) {
			DiffEntry src = nameMap.remove(dst.newPath);
			if (src != null) {
				if (sameType(src.oldMode
					entries.add(DiffEntry.pair(ChangeType.MODIFY
							src.score));
				} else {
					nameMap.put(src.oldPath
					newAdded.add(dst);
				}
			} else {
				newAdded.add(dst);
			}
			advanceOrCancel(pm);
		}

		added = newAdded;
		deleted = new ArrayList<>(nameMap.values());
	}

	private int calculateModifyScore(ContentSource.Pair reader
			throws IOException {
		try {
			SimilarityIndex src = new SimilarityIndex();
			src.hash(reader.open(OLD
			src.sort();

			SimilarityIndex dst = new SimilarityIndex();
			dst.hash(reader.open(NEW
			dst.sort();
			return src.score(dst
		} catch (TableFullException tableFull) {
			overRenameLimit = true;
			return breakScore + 1;
		}
	}

	private void findContentRenames(ContentSource.Pair reader
			ProgressMonitor pm)
			throws IOException
		int cnt = Math.max(added.size()
		if (getRenameLimit() == 0 || cnt <= getRenameLimit()) {
			SimilarityRenameDetector d;

			d = new SimilarityRenameDetector(reader
			d.setRenameScore(getRenameScore());
			d.compute(pm);
			overRenameLimit |= d.isTableOverflow();
			deleted = d.getLeftOverSources();
			added = d.getLeftOverDestinations();
			entries.addAll(d.getMatches());
		} else {
			overRenameLimit = true;
		}
	}

	@SuppressWarnings("unchecked")
	private void findExactRenames(ProgressMonitor pm)
			throws CancelledException {
		pm.beginTask(JGitText.get().renamesFindingExact
				added.size() + added.size() + deleted.size()
						+ added.size() * deleted.size());

		HashMap<AbbreviatedObjectId
		HashMap<AbbreviatedObjectId

		ArrayList<DiffEntry> uniqueAdds = new ArrayList<>(added.size());
		ArrayList<List<DiffEntry>> nonUniqueAdds = new ArrayList<>();

		for (Object o : addedMap.values()) {
			if (o instanceof DiffEntry)
				uniqueAdds.add((DiffEntry) o);
			else
				nonUniqueAdds.add((List<DiffEntry>) o);
		}

		ArrayList<DiffEntry> left = new ArrayList<>(added.size());

		for (DiffEntry a : uniqueAdds) {
			Object del = deletedMap.get(a.newId);
			if (del instanceof DiffEntry) {
				DiffEntry e = (DiffEntry) del;
				if (sameType(e.oldMode
					e.changeType = ChangeType.RENAME;
					entries.add(exactRename(e
				} else {
					left.add(a);
				}
			} else if (del != null) {
				List<DiffEntry> list = (List<DiffEntry>) del;
				DiffEntry best = bestPathMatch(a
				if (best != null) {
					best.changeType = ChangeType.RENAME;
					entries.add(exactRename(best
				} else {
					left.add(a);
				}
			} else {
				left.add(a);
			}
			advanceOrCancel(pm);
		}

		for (List<DiffEntry> adds : nonUniqueAdds) {
			Object o = deletedMap.get(adds.get(0).newId);
			if (o instanceof DiffEntry) {
				DiffEntry d = (DiffEntry) o;
				DiffEntry best = bestPathMatch(d
				if (best != null) {
					d.changeType = ChangeType.RENAME;
					entries.add(exactRename(d
					for (DiffEntry a : adds) {
						if (a != best) {
							if (sameType(d.oldMode
								entries.add(exactCopy(d
							} else {
								left.add(a);
							}
						}
					}
				} else {
					left.addAll(adds);
				}
			} else if (o != null) {
				List<DiffEntry> dels = (List<DiffEntry>) o;
				long[] matrix = new long[dels.size() * adds.size()];
				int mNext = 0;
				for (int delIdx = 0; delIdx < dels.size(); delIdx++) {
					String deletedName = dels.get(delIdx).oldPath;

					for (int addIdx = 0; addIdx < adds.size(); addIdx++) {
						String addedName = adds.get(addIdx).newPath;

						int score = SimilarityRenameDetector.nameScore(addedName
						matrix[mNext] = SimilarityRenameDetector.encode(score
						mNext++;
						if (pm.isCancelled()) {
							throw new CancelledException(
									JGitText.get().renameCancelled);
						}
					}
				}

				Arrays.sort(matrix);

				for (--mNext; mNext >= 0; mNext--) {
					long ent = matrix[mNext];
					int delIdx = SimilarityRenameDetector.srcFile(ent);
					int addIdx = SimilarityRenameDetector.dstFile(ent);
					DiffEntry d = dels.get(delIdx);
					DiffEntry a = adds.get(addIdx);

					if (a == null) {
						advanceOrCancel(pm);
					}

					ChangeType type;
					if (d.changeType == ChangeType.DELETE) {
						d.changeType = ChangeType.RENAME;
						type = ChangeType.RENAME;
					} else {
						type = ChangeType.COPY;
					}

					entries.add(DiffEntry.pair(type
					adds.set(addIdx
					advanceOrCancel(pm);
				}
			} else {
				left.addAll(adds);
			}
			advanceOrCancel(pm);
		}
		added = left;

		deleted = new ArrayList<>(deletedMap.size());
		for (Object o : deletedMap.values()) {
			if (o instanceof DiffEntry) {
				DiffEntry e = (DiffEntry) o;
				if (e.changeType == ChangeType.DELETE)
					deleted.add(e);
			} else {
				List<DiffEntry> list = (List<DiffEntry>) o;
				for (DiffEntry e : list) {
					if (e.changeType == ChangeType.DELETE)
						deleted.add(e);
				}
			}
		}
		pm.endTask();
	}

	private static DiffEntry bestPathMatch(DiffEntry src
		DiffEntry best = null;
		int score = -1;

		for (DiffEntry d : list) {
			if (sameType(mode(d)
				int tmp = SimilarityRenameDetector
						.nameScore(path(d)
				if (tmp > score) {
					best = d;
					score = tmp;
				}
			}
		}

		return best;
	}

	@SuppressWarnings("unchecked")
	private HashMap<AbbreviatedObjectId
			List<DiffEntry> diffEntries
			throws CancelledException {
		HashMap<AbbreviatedObjectId
		for (DiffEntry de : diffEntries) {
			Object old = map.put(id(de)
			if (old instanceof DiffEntry) {
				ArrayList<DiffEntry> list = new ArrayList<>(2);
				list.add((DiffEntry) old);
				list.add(de);
				map.put(id(de)
			} else if (old != null) {
				((List<DiffEntry>) old).add(de);
				map.put(id(de)
			}
			advanceOrCancel(pm);
		}
		return map;
	}

	private static String path(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldPath : de.newPath;
	}

	private static FileMode mode(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldMode : de.newMode;
	}

	private static AbbreviatedObjectId id(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldId : de.newId;
	}

	static boolean sameType(FileMode a
		int aType = a.getBits() & FileMode.TYPE_MASK;
		int bType = b.getBits() & FileMode.TYPE_MASK;
		return aType == bType;
	}

	private static DiffEntry exactRename(DiffEntry src
		return DiffEntry.pair(ChangeType.RENAME
	}

	private static DiffEntry exactCopy(DiffEntry src
		return DiffEntry.pair(ChangeType.COPY
	}
}
