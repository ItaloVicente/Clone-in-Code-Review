
package org.eclipse.jgit.revplot;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

public class PlotCommit<L extends PlotLane> extends RevCommit {
	static final PlotCommit[] NO_CHILDREN = {};

	static final PlotLane[] NO_LANES = {};

	static final Ref[] NO_REFS = {};

	PlotLane[] forkingOffLanes;

	PlotLane[] passingLanes;

	PlotLane[] mergingLanes;

	PlotLane lane;

	PlotCommit[] children;

	Ref[] refs;

	protected PlotCommit(AnyObjectId id) {
		super(id);
		forkingOffLanes = NO_LANES;
		passingLanes = NO_LANES;
		mergingLanes = NO_LANES;
		children = NO_CHILDREN;
		refs = NO_REFS;
	}

	void addForkingOffLane(PlotLane f) {
		forkingOffLanes = addLane(f
	}

	void addPassingLane(PlotLane c) {
		passingLanes = addLane(c
	}

	void addMergingLane(PlotLane m) {
		mergingLanes = addLane(m
	}

	private static PlotLane[] addLane(PlotLane l
		final int cnt = lanes.length;
		if (cnt == 0)
			lanes = new PlotLane[] { l };
		else if (cnt == 1)
			lanes = new PlotLane[] { lanes[0]
		else {
			final PlotLane[] n = new PlotLane[cnt + 1];
			System.arraycopy(lanes
			n[cnt] = l;
			lanes = n;
		}
		return lanes;
	}

	void addChild(PlotCommit c) {
		final int cnt = children.length;
		if (cnt == 0)
			children = new PlotCommit[] { c };
		else if (cnt == 1) {
			if (!c.getId().equals(children[0].getId()))
				children = new PlotCommit[] { children[0]
		} else {
			for (PlotCommit pc : children)
				if (c.getId().equals(pc.getId()))
					return;
			final PlotCommit[] n = new PlotCommit[cnt + 1];
			System.arraycopy(children
			n[cnt] = c;
			children = n;
		}
	}

	public final int getChildCount() {
		return children.length;
	}

	public final PlotCommit getChild(int nth) {
		return children[nth];
	}

	public final boolean isChild(PlotCommit c) {
		for (PlotCommit a : children)
			if (a == c)
				return true;
		return false;
	}

	public final int getRefCount() {
		return refs.length;
	}

	public final Ref getRef(int nth) {
		return refs[nth];
	}

	@SuppressWarnings("unchecked")
	public final L getLane() {
		return (L) lane;
	}

	@Override
	public void reset() {
		forkingOffLanes = NO_LANES;
		passingLanes = NO_LANES;
		mergingLanes = NO_LANES;
		children = NO_CHILDREN;
		lane = null;
		super.reset();
	}
}
