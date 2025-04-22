
package org.eclipse.jgit.revplot;

import java.text.MessageFormat;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommitList;
import org.eclipse.jgit.revwalk.RevWalk;

public class PlotCommitList<L extends PlotLane> extends
		RevCommitList<PlotCommit<L>> {
	static final int MAX_LENGTH = 25;

	private int positionsAllocated;

	private final TreeSet<Integer> freePositions = new TreeSet<>();

	private final HashSet<PlotLane> activeLanes = new HashSet<>(32);

	private final HashMap<PlotLane
			32);

	@Override
	public void clear() {
		super.clear();
		positionsAllocated = 0;
		freePositions.clear();
		activeLanes.clear();
		laneLength.clear();
	}

	@Override
	public void source(RevWalk w) {
		if (!(w instanceof PlotWalk))
			throw new ClassCastException(MessageFormat.format(JGitText.get().classCastNotA
		super.source(w);
	}

	@SuppressWarnings("unchecked")
	public void findPassingThrough(final PlotCommit<L> currCommit
			final Collection<L> result) {
		for (PlotLane p : currCommit.passingLanes)
			result.add((L) p);
	}

	@Override
	protected void enter(int index
		setupChildren(currCommit);

		final int nChildren = currCommit.getChildCount();
		if (nChildren == 0) {
			currCommit.lane = nextFreeLane();
		} else if (nChildren == 1
				&& currCommit.children[0].getParentCount() < 2) {

			@SuppressWarnings("unchecked")
			final PlotCommit<L> c = currCommit.children[0];
			currCommit.lane = c.lane;
			Integer len = laneLength.get(currCommit.lane);
			len = len != null ? Integer.valueOf(len.intValue() + 1)
					: Integer.valueOf(0);
			laneLength.put(currCommit.lane
		} else {



			PlotLane reservedLane = null;
			PlotCommit childOnReservedLane = null;
			int lengthOfReservedLane = -1;

			for (int i = 0; i < nChildren; i++) {
				@SuppressWarnings("unchecked")
				final PlotCommit<L> c = currCommit.children[i];
				if (c.getParent(0) == currCommit) {
					Integer len = laneLength.get(c.lane);
					if (len.intValue() > lengthOfReservedLane) {
						reservedLane = c.lane;
						childOnReservedLane = c;
						lengthOfReservedLane = len.intValue();
					}
				}
			}

			if (reservedLane != null) {
				currCommit.lane = reservedLane;
				laneLength.put(reservedLane
						Integer.valueOf(lengthOfReservedLane + 1));
				handleBlockedLanes(index
			} else {
				currCommit.lane = nextFreeLane();
				handleBlockedLanes(index
			}

			for (int i = 0; i < nChildren; i++) {
				final PlotCommit c = currCommit.children[i];
				PlotCommit firstParent = (PlotCommit) c.getParent(0);
				if (firstParent.lane != null && firstParent.lane != c.lane)
					closeLane(c.lane);
			}
		}

		continueActiveLanes(currCommit);
		if (currCommit.getParentCount() == 0)
			closeLane(currCommit.lane);
	}

	private void continueActiveLanes(PlotCommit currCommit) {
		for (PlotLane lane : activeLanes)
			if (lane != currCommit.lane)
				currCommit.addPassingLane(lane);
	}

	private void handleBlockedLanes(final int index
			final PlotCommit childOnLane) {
		for (PlotCommit child : currCommit.children) {
			if (child == childOnLane)

			boolean childIsMerge = child.getParent(0) != currCommit;
			if (childIsMerge) {
				PlotLane laneToUse = currCommit.lane;
				laneToUse = handleMerge(index
						laneToUse);
				child.addMergingLane(laneToUse);
			} else {
				PlotLane laneToUse = child.lane;
				currCommit.addForkingOffLane(laneToUse);
			}
		}
	}

	private PlotLane handleMerge(final int index
			final PlotCommit childOnLane


		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final PlotCommit rObj = get(r);
			if (rObj == child) {
				childIndex = r;
				break;
			}
			addBlockedPosition(blockedPositions
		}


		if (blockedPositions.get(laneToUse.getPosition())) {

			boolean needDetour = false;
			if (childOnLane != null) {
				for (int r = index - 1; r > childIndex; r--) {
					final PlotCommit rObj = get(r);
					if (rObj == childOnLane) {
						needDetour = true;
						break;
					}
				}
			}

			if (needDetour) {
				laneToUse = nextFreeLane(blockedPositions);
				currCommit.addForkingOffLane(laneToUse);
				closeLane(laneToUse);
			} else {
				int newPos = getFreePosition(blockedPositions);
				freePositions.add(Integer.valueOf(laneToUse
						.getPosition()));
				laneToUse.position = newPos;
			}
		}

		drawLaneToChild(index
		return laneToUse;
	}

	private void drawLaneToChild(final int commitIndex
			PlotLane laneToContinue) {
		for (int r = commitIndex - 1; r >= 0; r--) {
			final PlotCommit rObj = get(r);
			if (rObj == child)
				break;
			if (rObj != null)
				rObj.addPassingLane(laneToContinue);
		}
	}

	private static void addBlockedPosition(BitSet blockedPositions
			final PlotCommit rObj) {
		if (rObj != null) {
			PlotLane lane = rObj.getLane();
			if (lane != null)
				blockedPositions.set(lane.getPosition());
			for (PlotLane l : rObj.forkingOffLanes)
				blockedPositions.set(l.getPosition());
			for (PlotLane l : rObj.mergingLanes)
				blockedPositions.set(l.getPosition());
		}
	}

	@SuppressWarnings("unchecked")
	private void closeLane(PlotLane lane) {
		if (activeLanes.remove(lane)) {
			recycleLane((L) lane);
			laneLength.remove(lane);
			freePositions.add(Integer.valueOf(lane.getPosition()));
		}
	}

	private void setupChildren(PlotCommit<L> currCommit) {
		final int nParents = currCommit.getParentCount();
		for (int i = 0; i < nParents; i++)
			((PlotCommit) currCommit.getParent(i)).addChild(currCommit);
	}

	private PlotLane nextFreeLane() {
		return nextFreeLane(null);
	}

	private PlotLane nextFreeLane(BitSet blockedPositions) {
		final PlotLane p = createLane();
		p.position = getFreePosition(blockedPositions);
		activeLanes.add(p);
		laneLength.put(p
		return p;
	}

	private int getFreePosition(BitSet blockedPositions) {
		if (freePositions.isEmpty())
			return positionsAllocated++;

		if (blockedPositions != null) {
			for (Integer pos : freePositions)
				if (!blockedPositions.get(pos.intValue())) {
					freePositions.remove(pos);
					return pos.intValue();
				}
			return positionsAllocated++;
		} else {
			final Integer min = freePositions.first();
			freePositions.remove(min);
			return min.intValue();
		}
	}

	@SuppressWarnings("unchecked")
	protected L createLane() {
		return (L) new PlotLane();
	}

	protected void recycleLane(L lane) {
	}
}
