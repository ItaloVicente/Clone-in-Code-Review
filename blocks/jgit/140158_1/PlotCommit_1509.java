
package org.eclipse.jgit.revplot;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevFlag;

public abstract class AbstractPlotRenderer<TLane extends PlotLane
	private static final int LANE_WIDTH = 14;

	private static final int LINE_WIDTH = 2;

	private static final int LEFT_PAD = 2;

	@SuppressWarnings("unchecked")
	protected void paintCommit(PlotCommit<TLane> commit
		final int dotSize = computeDotSize(h);
		final TLane myLane = commit.getLane();
		final int myLaneX = laneC(myLane);
		final TColor myColor = laneColor(myLane);

		int maxCenter = myLaneX;
		for (TLane passingLane : (TLane[]) commit.passingLanes) {
			final int cx = laneC(passingLane);
			final TColor c = laneColor(passingLane);
			drawLine(c
			maxCenter = Math.max(maxCenter
		}

		final int dotX = myLaneX - dotSize / 2 - 1;
		final int dotY = (h - dotSize) / 2;

		final int nParent = commit.getParentCount();
		if (nParent > 0) {
			drawLine(myColor
					LINE_WIDTH);

                    for (PlotLane mergingLane : commit.mergingLanes) {
                        final TLane pLane = (TLane) mergingLane;
                        final TColor pColor = laneColor(pLane);
                        final int cx = laneC(pLane);
                        if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
                            final int ix;
                            if (myLaneX < cx)
                                ix = cx - LANE_WIDTH / 2;
                            else
                                ix = cx + LANE_WIDTH / 2;
                            
                            drawLine(pColor
                            drawLine(pColor
                        } else
                            drawLine(pColor
                        maxCenter = Math.max(maxCenter
                    }
		}


		if (commit.getChildCount() > 0) {
                    for (PlotLane forkingOffLane : commit.forkingOffLanes) {
                        final TLane childLane = (TLane) forkingOffLane;
                        final TColor cColor = laneColor(childLane);
                        final int cx = laneC(childLane);
                        if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
                            final int ix;
                            if (myLaneX < cx)
                                ix = cx - LANE_WIDTH / 2;
                            else
                                ix = cx + LANE_WIDTH / 2;
                            
                            drawLine(cColor
                            drawLine(cColor
                        } else {
                            drawLine(cColor
                        }
                        maxCenter = Math.max(maxCenter
                    }

			int nonForkingChildren = commit.getChildCount()
					- commit.forkingOffLanes.length;
			if (nonForkingChildren > 0)
						drawLine(myColor
		}

		if (commit.has(RevFlag.UNINTERESTING))
			drawBoundaryDot(dotX
		else
			drawCommitDot(dotX

		int textx = Math.max(maxCenter + LANE_WIDTH / 2
		int n = commit.refs.length;
		for (int i = 0; i < n; ++i) {
			textx += drawLabel(textx + dotSize
		}

		final String msg = commit.getShortMessage();
		drawText(msg
	}

	protected abstract int drawLabel(int x

	private static int computeDotSize(int h) {
		int d = (int) (Math.min(h
		d += (d & 1);
		return d;
	}

	protected abstract TColor laneColor(TLane myLane);

	protected abstract void drawLine(TColor color
			int y2

	protected abstract void drawCommitDot(int x

	protected abstract void drawBoundaryDot(int x

	protected abstract void drawText(String msg

	private static int laneX(PlotLane myLane) {
		final int p = myLane != null ? myLane.getPosition() : 0;
		return LEFT_PAD + LANE_WIDTH * p;
	}

	private static int laneC(PlotLane myLane) {
		return laneX(myLane) + LANE_WIDTH / 2;
	}
}
