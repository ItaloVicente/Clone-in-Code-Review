package org.eclipse.egit.core.synchronize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.treewalk.TreeWalk;

public final class ThreeWayDiffEntry {

	private static final AbbreviatedObjectId A_ZERO = AbbreviatedObjectId
			.fromObjectId(ObjectId.zeroId());

	public static enum ChangeType {
		ADD,

		MODIFY,

		DELETE,

		IN_SYNC;
	}

	public static enum Direction {
		INCOMING,

		OUTGOING,

		CONFLICTING;
	}

	ThreeWayDiffEntry() {
	}

	public static List<ThreeWayDiffEntry> scan(TreeWalk walk)
			throws IOException {
		if (walk.getTreeCount() != 3)
			throw new IllegalArgumentException(
					"TreeWalk need to have exactly three trees"); //$NON-NLS-1$
		if (walk.isRecursive())
			throw new IllegalArgumentException(
					"TreeWalk shouldn't be recursive."); //$NON-NLS-1$

		List<ThreeWayDiffEntry> r = new ArrayList<ThreeWayDiffEntry>();
		MutableObjectId idBuf = new MutableObjectId();
		while (walk.next()) {
			ThreeWayDiffEntry e = new ThreeWayDiffEntry();

			walk.getObjectId(idBuf, 0);
			e.localId = AbbreviatedObjectId.fromObjectId(idBuf);

			walk.getObjectId(idBuf, 1);
			e.baseId = AbbreviatedObjectId.fromObjectId(idBuf);

			walk.getObjectId(idBuf, 2);
			e.remoteId = AbbreviatedObjectId.fromObjectId(idBuf);

			boolean localSameAsBase = e.localId.equals(e.baseId);
			if (!A_ZERO.equals(e.localId) && localSameAsBase
					&& e.baseId.equals(e.remoteId))
				continue;

			e.path = walk.getPathString();
			boolean localIsMissing = walk.getFileMode(0) == FileMode.MISSING;
			boolean baseIsMissing = walk.getFileMode(1) == FileMode.MISSING;
			boolean remoteIsMissing = walk.getFileMode(2) == FileMode.MISSING;

			if (localIsMissing || baseIsMissing || remoteIsMissing) {
				if (!localIsMissing && baseIsMissing && remoteIsMissing) {
					e.direction = Direction.OUTGOING;
					e.changeType = ChangeType.ADD;
				} else if (localIsMissing && baseIsMissing && !remoteIsMissing) {
					e.direction = Direction.INCOMING;
					e.changeType = ChangeType.ADD;
				} else if (!localIsMissing && !baseIsMissing && remoteIsMissing) {
					e.direction = Direction.INCOMING;
					e.changeType = ChangeType.DELETE;
				} else if (localIsMissing && !baseIsMissing && !remoteIsMissing) {
					e.direction = Direction.OUTGOING;
					e.changeType = ChangeType.DELETE;
				} else {
					e.direction = Direction.CONFLICTING;
					e.changeType = ChangeType.MODIFY;
				}
			} else {
				if (localSameAsBase && !e.localId.equals(e.remoteId))
					e.direction = Direction.INCOMING;
				else if (e.remoteId.equals(e.baseId)
						&& !e.remoteId.equals(e.localId))
					e.direction = Direction.OUTGOING;
				else
					e.direction = Direction.CONFLICTING;

				e.changeType = ChangeType.MODIFY;
			}

			r.add(e);
			if (walk.isSubtree()) {
				e.isTree = true;
				walk.enterSubtree();
			}
		}

		return r;
	}

	ChangeType changeType;

	AbbreviatedObjectId baseId;

	AbbreviatedObjectId remoteId;

	private String path;

	private Direction direction;

	private AbbreviatedObjectId localId;

	private boolean isTree = false;

	public AbbreviatedObjectId getBaseId() {
		return baseId;
	}

	public String getPath() {
		return path;
	}

	public boolean isTree() {
		return isTree;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public AbbreviatedObjectId getLocalId() {
		return localId;
	}

	public AbbreviatedObjectId getRemoteId() {
		return remoteId;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("ThreeDiffEntry["); //$NON-NLS-1$
		buf.append(changeType).append(" ").append(path); //$NON-NLS-1$
		buf.append("]"); //$NON-NLS-1$

		return buf.toString();
	}

}
