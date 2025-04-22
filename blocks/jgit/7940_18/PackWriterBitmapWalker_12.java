
package org.eclipse.jgit.storage.pack;

import static org.eclipse.jgit.storage.file.PackBitmapIndex.FLAG_REUSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javaewah.EWAHCompressedBitmap;

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
import org.eclipse.jgit.storage.file.PackBitmapIndex;
import org.eclipse.jgit.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.storage.file.PackBitmapIndexRemapper;
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
	private final BitmapIndexImpl commitBitmapIndex;
	private final PackBitmapIndexRemapper bitmapRemapper;
	private final BitmapIndexImpl bitmapIndex;
	private final int minCommits = 100;
	private final int maxCommits = 5000;

	PackWriterBitmapPreparer(ObjectReader reader
			PackBitmapIndexBuilder writeBitmaps
			Set<? extends ObjectId> want) throws IOException {
		this.reader = reader;
		this.writeBitmaps = writeBitmaps;
		this.pm = pm;
		this.want = want;
		this.commitBitmapIndex = new BitmapIndexImpl(writeBitmaps);
		this.bitmapRemapper = PackBitmapIndexRemapper.newPackBitmapIndex(
				reader.getBitmapIndex()
		this.bitmapIndex = new BitmapIndexImpl(bitmapRemapper);
	}

	Collection<BitmapCommit> doCommitSelection(int expectedNumCommits)
			throws MissingObjectException
			IOException {
		pm.beginTask(JGitText.get().selectingCommits
		RevWalk rw = new RevWalk(reader);
		WalkResult result = findPaths(rw
		pm.endTask();

		int totCommits = result.commitsByOldest.length - result.commitStartPos;
		BlockList<BitmapCommit> selections = new BlockList<BitmapCommit>(
				totCommits / minCommits + 1);
		for (BitmapCommit reuse : result.reuse)
			selections.add(reuse);

		if (totCommits == 0) {
			for (AnyObjectId id : result.peeledWant)
				selections.add(new BitmapCommit(id
			return selections;
		}

		pm.beginTask(JGitText.get().selectingCommits

		for (BitmapBuilder bitmapableCommits : result.paths) {
			int cardinality = bitmapableCommits.cardinality();

			List<List<BitmapCommit>> running = new ArrayList<
					List<BitmapCommit>>();

			int index = -1;
			int nextIn = nextSelectionDistance(0
			int nextFlg = nextIn == maxCommits ? PackBitmapIndex.FLAG_REUSE : 0;
			boolean mustPick = nextIn == 0;
			for (RevCommit c : result) {
				if (!bitmapableCommits.contains(c))
					continue;

				index++;
				nextIn--;
				pm.update(1);

				if (result.peeledWant.remove(c)) {
					if (nextIn > 0)
						nextFlg = 0;
				} else if (!mustPick && ((nextIn > 0)
						|| (c.getParentCount() <= 1 && nextIn > -minCommits))) {
					continue;
				}

				int flags = nextFlg;
				nextIn = nextSelectionDistance(index
				nextFlg = nextIn == maxCommits ? PackBitmapIndex.FLAG_REUSE : 0;
				mustPick = nextIn == 0;

				BitmapBuilder fullBitmap = commitBitmapIndex.newBitmapBuilder();
				rw.reset();
				rw.markStart(c);
				for (AnyObjectId objectId : result.reuse)
					rw.markUninteresting(rw.parseCommit(objectId));
				rw.setRevFilter(
						PackWriterBitmapWalker.newRevFilter(null

				while (rw.next() != null) {
				}

				List<List<BitmapCommit>> matches = new ArrayList<
						List<BitmapCommit>>();
				for (List<BitmapCommit> list : running) {
					BitmapCommit last = list.get(list.size() - 1);
					if (fullBitmap.contains(last))
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
		BitmapBuilder reuseBitmap = commitBitmapIndex.newBitmapBuilder();
		List<BitmapCommit> reuse = new ArrayList<BitmapCommit>();
		for (PackBitmapIndexRemapper.Entry entry : bitmapRemapper) {
			if ((entry.getFlags() & FLAG_REUSE) != FLAG_REUSE)
				continue;

			RevObject ro = rw.peel(rw.parseAny(entry));
			if (ro instanceof RevCommit) {
				RevCommit rc = (RevCommit) ro;
				reuse.add(new BitmapCommit(rc
				rw.markUninteresting(rc);

				EWAHCompressedBitmap bitmap = bitmapRemapper.ofObjectType(
						bitmapRemapper.getBitmap(rc)
				writeBitmaps.addBitmap(rc
				reuseBitmap.add(rc
			}
		}

		List<BitmapBuilder> paths = new ArrayList<BitmapBuilder>(want.size());
		Set<RevCommit> peeledWant = new HashSet<RevCommit>(want.size());
		for (AnyObjectId objectId : want) {
			RevObject ro = rw.peel(rw.parseAny(objectId));
			if (ro instanceof RevCommit && !reuseBitmap.contains(ro)) {
				RevCommit rc = (RevCommit) ro;
				peeledWant.add(rc);
				rw.markStart(rc);

				BitmapBuilder bitmap = commitBitmapIndex.newBitmapBuilder();
				bitmap.or(reuseBitmap);
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

		if (!reuse.isEmpty())
			for (BitmapBuilder bitmap : paths)
				bitmap.andNot(reuseBitmap);

		List<BitmapBuilder> distinctPaths = new ArrayList<BitmapBuilder>(paths.size());
		while (!paths.isEmpty()) {
			Collections.sort(paths
			BitmapBuilder largest = paths.remove(0);
			distinctPaths.add(largest);

			for (int i = paths.size() - 1; i >= 0; i--)
				paths.get(i).andNot(largest);
		}

		return new WalkResult(peeledWant
	}

	private int nextSelectionDistance(int idx
		if (idx > cardinality)
			throw new IllegalArgumentException();
		int idxFromStart = cardinality - idx;
		int mustRegionEnd = 100;
		if (idxFromStart <= mustRegionEnd)
			return 0;

		int minRegionEnd = 20000;
		if (idxFromStart <= minRegionEnd)
			return Math.min(idxFromStart - mustRegionEnd

		int next = Math.min(idxFromStart - minRegionEnd
		return Math.max(next
	}

	PackWriterBitmapWalker newBitmapWalker() {
		return new PackWriterBitmapWalker(
				new ObjectWalk(reader)
	}

	static final class BitmapCommit extends ObjectId {
		private final boolean reuseWalker;
		private final int flags;

		private BitmapCommit(
				AnyObjectId objectId
			super(objectId);
			this.reuseWalker = reuseWalker;
			this.flags = flags;
		}

		boolean isReuseWalker() {
			return reuseWalker;
		}

		int getFlags() {
			return flags;
		}
	}

	private static final class WalkResult implements Iterable<RevCommit> {
		private final Set<? extends ObjectId> peeledWant;
		private final RevCommit[] commitsByOldest;
		private final int commitStartPos;
		private final List<BitmapBuilder> paths;
		private final Iterable<BitmapCommit> reuse;

		private WalkResult(Set<? extends ObjectId> peeledWant
				RevCommit[] commitsByOldest
				List<BitmapBuilder> paths
			this.peeledWant = peeledWant;
			this.commitsByOldest = commitsByOldest;
			this.commitStartPos = commitStartPos;
			this.paths = paths;
			this.reuse = reuse;
		}

		public Iterator<RevCommit> iterator() {
			return new Iterator<RevCommit>() {
				int pos = commitStartPos;

				public boolean hasNext() {
					return pos < commitsByOldest.length;
				}

				public RevCommit next() {
					return commitsByOldest[pos++];
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}
}
