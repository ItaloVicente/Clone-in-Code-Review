package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.compare.structuremergeviewer.Differencer.RIGHT;


public final class SupportedContextActionsHelper {

	private SupportedContextActionsHelper() {
	}

	public static boolean canCommit(GitModelObject object) {
		return object instanceof GitModelWorkingFile
				|| object instanceof GitModelCacheFile
				|| object instanceof GitModelCacheTree
				|| object instanceof GitModelCache;
	}

	public static boolean canUseMergeTool(GitModelObject object) {
		return object instanceof GitModelWorkingFile
				|| object instanceof GitModelCacheFile;
	}

	public static boolean canStage(GitModelObject object) {
		return object instanceof GitModelWorkingFile
				|| getRootObject(object) instanceof GitModelWorkingTree;
	}

	public static boolean canAssumeUnchanged(GitModelObject object) {
		return canStage(object) || object instanceof GitModelCacheFile
				|| object instanceof GitModelCacheTree;
	}

	public static boolean canReset(GitModelObject object) {
		return isOutGoingCommit(object) || canCommit(object);
	}

	public static boolean canPush(GitModelObject object) {
		return isOutGoingCommit(object);
	}

	public static boolean canMerge(GitModelObject object) {
		return getRootObject(object) instanceof GitModelCommit;
	}

	private static GitModelObject getRootObject(GitModelObject object) {
		GitModelObject root = object.getParent();

		while (root != null && root instanceof GitModelTree)
			root = root.getParent();

		return root;
	}

	private static boolean isOutGoingCommit(GitModelObject object) {
		int direction = LEFT;
		if (getRootObject(object) instanceof GitModelCommit)
			direction = ((GitModelCommit) object).getKind() & RIGHT;

		return direction == RIGHT;
	}

}
