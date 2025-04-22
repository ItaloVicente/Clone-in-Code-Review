
package org.eclipse.jgit.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;

public class RenameDetector {
	private static final int EXACT_RENAME_SCORE = 100;

	private static final Comparator<DiffEntry> DIFF_COMPARATOR = new Comparator<DiffEntry>() {
		public int compare(DiffEntry a
			int cmp = nameOf(a).compareTo(nameOf(b));
			if (cmp == 0)
				cmp = sortOf(a.getChangeType()) - sortOf(b.getChangeType());
			return cmp;
		}

		private String nameOf(DiffEntry ent) {
			if (ent.changeType == ChangeType.DELETE)
				return ent.oldName;
			return ent.newName;
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

	private final List<DiffEntry> entries = new ArrayList<DiffEntry>();

	private List<DiffEntry> deleted = new ArrayList<DiffEntry>();

	private List<DiffEntry> added = new ArrayList<DiffEntry>();

	private boolean done;

	private final Repository repo;

	private int renameScore = 60;

	private int renameLimit;

	private boolean overRenameLimit;

	public RenameDetector(Repository repo) {
		this.repo = repo;

		DiffConfig cfg = repo.getConfig().get(DiffConfig.KEY);
		renameLimit = cfg.getRenameLimit();
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
				else
					entries.addAll(DiffEntry.breakModify(entry));
				break;

			case COPY:
			case RENAME:
			default:
				entriesToAdd.add(entry);
			}
		}
	}

	public void add(DiffEntry entry) {
		addAll(Collections.singletonList(entry));
	}

	public List<DiffEntry> compute() throws IOException {
		return compute(NullProgressMonitor.INSTANCE);
	}

	public List<DiffEntry> compute(ProgressMonitor pm) throws IOException {
		if (!done) {
			done = true;

			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
			findExactRenames(pm);
			findContentRenames(pm);

			entries.addAll(added);
			added = null;

			entries.addAll(deleted);
			deleted = null;

			Collections.sort(entries
		}
		return Collections.unmodifiableList(entries);
	}

	private void findContentRenames(ProgressMonitor pm) throws IOException {
		int cnt = Math.max(added.size()
		if (cnt == 0)
			return;

		if (getRenameLimit() == 0 || cnt <= getRenameLimit()) {
			SimilarityRenameDetector d;

			d = new SimilarityRenameDetector(repo
			d.setRenameScore(getRenameScore());
			d.compute(pm);
			deleted = d.getLeftOverSources();
			added = d.getLeftOverDestinations();
			entries.addAll(d.getMatches());
		} else {
			overRenameLimit = true;
		}
	}

	@SuppressWarnings("unchecked")
	private void findExactRenames(ProgressMonitor pm) {
		if (added.isEmpty() || deleted.isEmpty())
			return;

		pm.beginTask(JGitText.get().renamesFindingExact
				added.size() + added.size() + deleted.size()
						+ added.size() * deleted.size());

		HashMap<AbbreviatedObjectId
		HashMap<AbbreviatedObjectId

		ArrayList<DiffEntry> uniqueAdds = new ArrayList<DiffEntry>(added.size());
		ArrayList<List<DiffEntry>> nonUniqueAdds = new ArrayList<List<DiffEntry>>();

		for (Object o : addedMap.values()) {
			if (o instanceof DiffEntry)
				uniqueAdds.add((DiffEntry) o);
			else
				nonUniqueAdds.add((List<DiffEntry>) o);
		}

		ArrayList<DiffEntry> left = new ArrayList<DiffEntry>(added.size());

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
			pm.update(1);
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
			} else {
				List<DiffEntry> dels = (List<DiffEntry>) o;
				long[] matrix = new long[dels.size() * adds.size()];
				int mNext = 0;
				for (int addIdx = 0; addIdx < adds.size(); addIdx++) {
					String addedName = adds.get(addIdx).newName;

					for (int delIdx = 0; delIdx < dels.size(); delIdx++) {
						String deletedName = dels.get(delIdx).oldName;

						int score = SimilarityRenameDetector.nameScore(addedName
						matrix[mNext] = SimilarityRenameDetector.encode(score
						mNext++;
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
						pm.update(1);
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
					pm.update(1);
				}
			}
		}
		added = left;

		deleted = new ArrayList<DiffEntry>(deletedMap.size());
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
		HashMap<AbbreviatedObjectId
		for (DiffEntry de : diffEntries) {
			Object old = map.put(id(de)
			if (old instanceof DiffEntry) {
				ArrayList<DiffEntry> list = new ArrayList<DiffEntry>(2);
				list.add((DiffEntry) old);
				list.add(de);
				map.put(id(de)
			} else if (old != null) {
				((List<DiffEntry>) old).add(de);
				map.put(id(de)
			}
			pm.update(1);
		}
		return map;
	}

	private static String path(DiffEntry de) {
		return de.changeType == ChangeType.DELETE ? de.oldName : de.newName;
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
