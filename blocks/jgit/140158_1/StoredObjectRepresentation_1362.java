
package org.eclipse.jgit.internal.storage.pack;

import static org.eclipse.jgit.internal.storage.file.PackBitmapIndex.FLAG_REUSE;
import static org.eclipse.jgit.revwalk.RevFlag.SEEN;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.revwalk.AddUnseenToBitmapFilter;
import org.eclipse.jgit.internal.storage.file.BitmapIndexImpl;
import org.eclipse.jgit.internal.storage.file.BitmapIndexImpl.CompressedBitmap;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndex;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexRemapper;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.BitmapWalker;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.util.BlockList;
import org.eclipse.jgit.util.SystemReader;

import com.googlecode.javaewah.EWAHCompressedBitmap;

class PackWriterBitmapPreparer {

	private static final int DAY_IN_SECONDS = 24 * 60 * 60;

	private static final Comparator<RevCommit> ORDER_BY_REVERSE_TIMESTAMP = new Comparator<RevCommit>() {
		@Override
		public int compare(RevCommit a
			return Integer.signum(b.getCommitTime() - a.getCommitTime());
		}
	};

	private final ObjectReader reader;
	private final ProgressMonitor pm;
	private final Set<? extends ObjectId> want;
	private final PackBitmapIndexBuilder writeBitmaps;
	private final BitmapIndexImpl commitBitmapIndex;
	private final PackBitmapIndexRemapper bitmapRemapper;
	private final BitmapIndexImpl bitmapIndex;

	private final int contiguousCommitCount;
	private final int recentCommitCount;
	private final int recentCommitSpan;
	private final int distantCommitSpan;
	private final int excessiveBranchCount;
	private final long inactiveBranchTimestamp;

	PackWriterBitmapPreparer(ObjectReader reader
			PackBitmapIndexBuilder writeBitmaps
			Set<? extends ObjectId> want
					throws IOException {
		this.reader = reader;
		this.writeBitmaps = writeBitmaps;
		this.pm = pm;
		this.want = want;
		this.commitBitmapIndex = new BitmapIndexImpl(writeBitmaps);
		this.bitmapRemapper = PackBitmapIndexRemapper.newPackBitmapIndex(
				reader.getBitmapIndex()
		this.bitmapIndex = new BitmapIndexImpl(bitmapRemapper);
		this.contiguousCommitCount = config.getBitmapContiguousCommitCount();
		this.recentCommitCount = config.getBitmapRecentCommitCount();
		this.recentCommitSpan = config.getBitmapRecentCommitSpan();
		this.distantCommitSpan = config.getBitmapDistantCommitSpan();
		this.excessiveBranchCount = config.getBitmapExcessiveBranchCount();
		long now = SystemReader.getInstance().getCurrentTime();
		long ageInSeconds = config.getBitmapInactiveBranchAgeInDays()
		this.inactiveBranchTimestamp = (now / 1000) - ageInSeconds;
	}

	Collection<BitmapCommit> selectCommits(int expectedCommitCount
			Set<? extends ObjectId> excludeFromBitmapSelection)
			throws IncorrectObjectTypeException
			MissingObjectException {
		try (RevWalk rw = new RevWalk(reader);
				RevWalk rw2 = new RevWalk(reader)) {
			pm.beginTask(JGitText.get().selectingCommits
					ProgressMonitor.UNKNOWN);
			rw.setRetainBody(false);
			CommitSelectionHelper selectionHelper = captureOldAndNewCommits(rw
					expectedCommitCount
			pm.endTask();

			int newCommits = selectionHelper.getCommitCount();
			BlockList<BitmapCommit> selections = new BlockList<>(
					selectionHelper.reusedCommits.size()
							+ newCommits / recentCommitSpan + 1);
			for (BitmapCommit reuse : selectionHelper.reusedCommits) {
				selections.add(reuse);
			}

			if (newCommits == 0) {
				for (AnyObjectId id : selectionHelper.newWants) {
					selections.add(new BitmapCommit(id
				}
				return selections;
			}

			pm.beginTask(JGitText.get().selectingCommits
			int totalWants = want.size();
			BitmapBuilder seen = commitBitmapIndex.newBitmapBuilder();
			seen.or(selectionHelper.reusedCommitsBitmap);
			rw2.setRetainBody(false);
			rw2.setRevFilter(new NotInBitmapFilter(seen));

			for (RevCommit rc : selectionHelper.newWantsByNewest) {
				BitmapBuilder tipBitmap = commitBitmapIndex.newBitmapBuilder();
				rw2.markStart((RevCommit) rw2.peel(rw2.parseAny(rc)));
				RevCommit rc2;
				while ((rc2 = rw2.next()) != null) {
					tipBitmap.addObject(rc2
				}
				int cardinality = tipBitmap.cardinality();
				seen.or(tipBitmap);

				List<List<BitmapCommit>> chains = new ArrayList<>();

				boolean isActiveBranch = true;
				if (totalWants > excessiveBranchCount && !isRecentCommit(rc)) {
					isActiveBranch = false;
				}

				int index = -1;
				int nextIn = nextSpan(cardinality);
				int nextFlg = nextIn == distantCommitSpan
						? PackBitmapIndex.FLAG_REUSE
						: 0;

				for (RevCommit c : selectionHelper) {
					int distanceFromTip = cardinality - index - 1;
					if (distanceFromTip == 0) {
						break;
					}

					if (!tipBitmap.contains(c)) {
						continue;
					}

					index++;
					nextIn--;
					pm.update(1);

					if (selectionHelper.newWants.remove(c)) {
						if (nextIn > 0) {
							nextFlg = 0;
						}
					} else {
						boolean stillInSpan = nextIn >= 0;
						boolean isMergeCommit = c.getParentCount() > 1;
						boolean mustPick = (nextIn <= -recentCommitSpan)
								|| (isActiveBranch
										&& (distanceFromTip <= contiguousCommitCount))
						if (!mustPick && (stillInSpan || !isMergeCommit)) {
							continue;
						}
					}

					int flags = nextFlg;
					nextIn = nextSpan(distanceFromTip);
					nextFlg = nextIn == distantCommitSpan
							? PackBitmapIndex.FLAG_REUSE
							: 0;

					BitmapBuilder bitmap = commitBitmapIndex.newBitmapBuilder();
					rw.reset();
					rw.markStart(c);
					rw.setRevFilter(new AddUnseenToBitmapFilter(
							selectionHelper.reusedCommitsBitmap
					while (rw.next() != null) {
					}

					List<BitmapCommit> longestAncestorChain = null;
					for (List<BitmapCommit> chain : chains) {
						BitmapCommit mostRecentCommit = chain
								.get(chain.size() - 1);
						if (bitmap.contains(mostRecentCommit)) {
							if (longestAncestorChain == null
									|| longestAncestorChain.size() < chain
											.size()) {
								longestAncestorChain = chain;
							}
						}
					}

					if (longestAncestorChain == null) {
						longestAncestorChain = new ArrayList<>();
						chains.add(longestAncestorChain);
					}
					longestAncestorChain.add(new BitmapCommit(c
							!longestAncestorChain.isEmpty()
					writeBitmaps.addBitmap(c
				}

				for (List<BitmapCommit> chain : chains) {
					selections.addAll(chain);
				}
			}

			for (AnyObjectId remainingWant : selectionHelper.newWants) {
				selections.add(new BitmapCommit(remainingWant
			}

			pm.endTask();
			return selections;
		}
	}

	private boolean isRecentCommit(RevCommit revCommit) {
		return revCommit.getCommitTime() > inactiveBranchTimestamp;
	}

	private static class NotInBitmapFilter extends RevFilter {
		private final BitmapBuilder bitmap;

		NotInBitmapFilter(BitmapBuilder bitmap) {
			this.bitmap = bitmap;
		}

		@Override
		public final boolean include(RevWalk rw
			if (!bitmap.contains(c)) {
				return true;
			}
			for (RevCommit p : c.getParents()) {
				p.add(SEEN);
			}
			return false;
		}

		@Override
		public final NotInBitmapFilter clone() {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean requiresCommitBody() {
			return false;
		}
	}

	private CommitSelectionHelper captureOldAndNewCommits(RevWalk rw
			int expectedCommitCount
			Set<? extends ObjectId> excludeFromBitmapSelection)
			throws IncorrectObjectTypeException
			MissingObjectException {
		BitmapBuilder reuse = commitBitmapIndex.newBitmapBuilder();
		List<BitmapCommit> reuseCommits = new ArrayList<>();
		for (PackBitmapIndexRemapper.Entry entry : bitmapRemapper) {
			if ((entry.getFlags() & FLAG_REUSE) != FLAG_REUSE) {
				continue;
			}
			RevObject ro = rw.peel(rw.parseAny(entry));
			if (!(ro instanceof RevCommit)) {
				continue;
			}

			RevCommit rc = (RevCommit) ro;
			reuseCommits.add(new BitmapCommit(rc
			if (!reuse.contains(rc)) {
				EWAHCompressedBitmap bitmap = bitmapRemapper.ofObjectType(
						bitmapRemapper.getBitmap(rc)
				reuse.or(new CompressedBitmap(bitmap
			}
		}

		List<RevCommit> newWantsByNewest = new ArrayList<>(want.size());
		Set<RevCommit> newWants = new HashSet<>(want.size());
		for (AnyObjectId objectId : want) {
			RevObject ro = rw.peel(rw.parseAny(objectId));
			if (!(ro instanceof RevCommit) || reuse.contains(ro)
					|| excludeFromBitmapSelection.contains(ro)) {
				continue;
			}

			RevCommit rc = (RevCommit) ro;
			rw.markStart(rc);
			newWants.add(rc);
			newWantsByNewest.add(rc);
		}

		rw.setRevFilter(new NotInBitmapFilter(reuse));
		RevCommit[] commits = new RevCommit[expectedCommitCount];
		int pos = commits.length;
		RevCommit rc;
		while ((rc = rw.next()) != null && pos > 0) {
			commits[--pos] = rc;
			pm.update(1);
		}

		Collections.sort(newWantsByNewest
		return new CommitSelectionHelper(newWants
				newWantsByNewest
	}

	int nextSpan(int distanceFromTip) {
		if (distanceFromTip < 0) {
			throw new IllegalArgumentException();
		}

		if (distanceFromTip <= recentCommitCount) {
			return recentCommitSpan;
		}

		int next = Math.min(distanceFromTip - recentCommitCount
				distantCommitSpan);
		return Math.max(next
	}

	BitmapWalker newBitmapWalker() {
		return new BitmapWalker(
				new ObjectWalk(reader)
	}

	static final class BitmapCommit extends ObjectId {
		private final boolean reuseWalker;
		private final int flags;

		BitmapCommit(AnyObjectId objectId
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

	private static final class CommitSelectionHelper implements Iterable<RevCommit> {
		final Set<? extends ObjectId> newWants;

		final List<RevCommit> newWantsByNewest;
		final BitmapBuilder reusedCommitsBitmap;

		final List<BitmapCommit> reusedCommits;

		final RevCommit[] newCommitsByOldest;

		final int newCommitStartPos;

		CommitSelectionHelper(Set<? extends ObjectId> newWants
				RevCommit[] commitsByOldest
				List<RevCommit> newWantsByNewest
				BitmapBuilder reusedCommitsBitmap
				List<BitmapCommit> reuse) {
			this.newWants = newWants;
			this.newCommitsByOldest = commitsByOldest;
			this.newCommitStartPos = commitStartPos;
			this.newWantsByNewest = newWantsByNewest;
			this.reusedCommitsBitmap = reusedCommitsBitmap;
			this.reusedCommits = reuse;
		}

		@Override
		public Iterator<RevCommit> iterator() {
			return new Iterator<RevCommit>() {
				int pos = newCommitStartPos;

				@Override
				public boolean hasNext() {
					return pos < newCommitsByOldest.length;
				}

				@Override
				public RevCommit next() {
					return newCommitsByOldest[pos++];
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		int getCommitCount() {
			return newCommitsByOldest.length - newCommitStartPos;
		}
	}
}
