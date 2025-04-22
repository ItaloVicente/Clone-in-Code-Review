
package org.eclipse.jgit.storage.pack;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.BitmapIndexImpl;
import org.eclipse.jgit.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.util.BlockList;

class PackWriterBitmapPreparer {

	private static final Comparator<BitmapBuilder> BUILDER_BY_CARDINALITY_DSC =
			new Comparator<BitmapBuilder>() {
		public int compare(BitmapBuilder a
			return Integer.signum(b.cardinality() - a.cardinality());
		}
	};

	private final ObjectReader reader;

	private final ProgressMonitor pm;

	private final Set<? extends ObjectId> want;

	private final PackBitmapIndexBuilder writeBitmaps;

	private final BitmapIndexImpl bitmapIndex;

	private final int minCommits = 100;

	private final int maxCommits = 10000;

	PackWriterBitmapPreparer(ObjectReader reader
			PackBitmapIndexBuilder writeBitmaps
			Set<? extends ObjectId> want) {
		this.reader = reader;
		this.writeBitmaps = writeBitmaps;
		this.pm = pm;
		this.want = want;
		this.bitmapIndex = new BitmapIndexImpl(writeBitmaps);
	}

	Collection<BitmapCommit> doCommitSelection(int expectedNumCommits)
			throws MissingObjectException
			IOException {
		pm.beginTask(JGitText.get().selectingCommits
		RevWalk rw = new RevWalk(reader);
		WalkResult result = findPaths(rw
		pm.endTask();

		int totalCommits = 0;
		for (BitmapBuilder bitmapableCommits : result.paths)
			totalCommits += bitmapableCommits.cardinality();

		if (totalCommits == 0)
			return Collections.emptyList();

		pm.beginTask(JGitText.get().selectingCommits

		BlockList<BitmapCommit> selections = new BlockList<BitmapCommit>(
				totalCommits / minCommits + 1);
		for (BitmapBuilder bitmapableCommits : result.paths) {
			int cardinality = bitmapableCommits.cardinality();

			List<List<BitmapCommit>> running = new ArrayList<
					List<BitmapCommit>>();

			int index = -1;
			int nextIn = nextSelectionDistance(0
			for (RevCommit c : result.commitsByOldest) {
				if (!bitmapableCommits.contains(c))
					continue;

				index++;
				nextIn--;
				pm.update(1);

				boolean mustPick = result.peeledWant.remove(c);
				if (!mustPick && ((nextIn > 0)
						|| (c.getParentCount() <= 1 && nextIn > -minCommits)))
					continue;


				nextIn = nextSelectionDistance(index

				BitmapBuilder fullBitmap = bitmapIndex.newBitmapBuilder();
				rw.reset();
				rw.markStart(c);
				rw.setRevFilter(
						PackWriterBitmapWalker.newRevFilter(null

				while (rw.next() != null) {
				}

				List<List<BitmapCommit>> matches = new ArrayList<
						List<BitmapCommit>>();
				for (List<BitmapCommit> list : running) {
					BitmapCommit last = list.get(list.size() - 1);
					if (fullBitmap.contains(last.getObjectId()))
						matches.add(list);
				}

				List<BitmapCommit> match;
				if (matches.isEmpty()) {
					match = new ArrayList<BitmapCommit>();
					running.add(match);
				} else {
					match = matches.get(0);
					for (List<BitmapCommit> list : matches) {
						if (list.size() > match.size())
							match = list;
					}
				}
				match.add(new BitmapCommit(c
				writeBitmaps.addBitmap(c
			}

			for (List<BitmapCommit> list : running)
				selections.addAll(list);
		}


		for (AnyObjectId remainingWant : result.peeledWant)
			selections.add(new BitmapCommit(remainingWant

		pm.endTask();
		return selections;
	}

	private WalkResult findPaths(RevWalk rw
			throws MissingObjectException
		List<BitmapBuilder> paths = new ArrayList<BitmapBuilder>(want.size());
		Set<RevCommit> peeledWant = new HashSet<RevCommit>(want.size());
		for (AnyObjectId objectId : want) {
			RevObject ro = rw.peel(rw.parseAny(objectId));
			if (ro instanceof RevCommit) {
				RevCommit rc = (RevCommit) ro;
				peeledWant.add(rc);
				rw.markStart(rc);

				BitmapBuilder bitmap = bitmapIndex.newBitmapBuilder();
				bitmap.add(rc
				paths.add(bitmap);
			}
		}

		RevCommit[] commits = new RevCommit[expectedNumCommits];
		int pos = commits.length;
		RevCommit rc;
		while ((rc = rw.next()) != null) {
			commits[--pos] = rc;
			for (BitmapBuilder path : paths) {
				if (path.contains(rc)) {
					for (RevCommit c : rc.getParents())
						path.add(c
				}
			}

			pm.update(1);
		}

		if (pos != 0)
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().expectedGot

		List<BitmapBuilder> distinctPaths = new ArrayList<BitmapBuilder>(paths.size());
		while (!paths.isEmpty()) {
			Collections.sort(paths
			BitmapBuilder largest = paths.remove(0);
			int minSize = 2 * minCommits;
			if (largest.cardinality() < minSize)
				break;
			distinctPaths.add(largest);

			for (int i = paths.size() - 1; i >= 0; i--) {
				if (paths.get(i).andNot(largest).cardinality() < minSize)
					paths.remove(i);
			}
		}

		return new WalkResult(peeledWant
	}


	private int nextSelectionDistance(int idx
		if (idx > cardinality)
			throw new IllegalArgumentException();
		int idxFromStart = cardinality - idx;
		int shift = 200 * minCommits;
		if (cardinality <= shift || idxFromStart <= shift)
			return minCommits;

		int shiftedCardinality = cardinality - shift;
		long shiftedIdxFromStart = idxFromStart - shift;
		long numerator = shiftedIdxFromStart * (maxCommits - minCommits);
		int minDesired = (int) (numerator / shiftedCardinality + minCommits);
		int minAllowed = Math.max(shiftedCardinality / 2
		return Math.min(minDesired
	}

	PackWriterBitmapWalker newBitmapWalker() {
		return new PackWriterBitmapWalker(
				new ObjectWalk(reader)
	}

	static final class BitmapCommit {
		private final ObjectId objectId;

		private final boolean reuseWalker;

		private BitmapCommit(AnyObjectId objectId
			this.objectId = objectId.toObjectId();
			this.reuseWalker = reuseWalker;
		}

		ObjectId getObjectId() {
			return objectId;
		}

		boolean isReuseWalker() {
			return reuseWalker;
		}
	}

	private static final class WalkResult {
		private final RevCommit[] commitsByOldest;

		private final List<BitmapBuilder> paths;

		private final Set<? extends ObjectId> peeledWant;

		private WalkResult(Set<? extends ObjectId> peeledWant
				RevCommit[] commitsByOldest
			this.peeledWant = peeledWant;
			this.commitsByOldest = commitsByOldest;
			this.paths = paths;
		}
	}
}
