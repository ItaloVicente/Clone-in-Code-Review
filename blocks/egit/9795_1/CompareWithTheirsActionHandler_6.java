
package org.eclipse.egit.ui.internal.actions;

import org.eclipse.jgit.dircache.DirCacheEntry;

public class CompareWithOursActionHandler extends
		CompareWithIndexStageActionHandler {

	@Override
	protected int getStage() {
		return DirCacheEntry.STAGE_2;
	}

}
