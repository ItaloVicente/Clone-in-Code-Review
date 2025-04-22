package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.core.op.DiscardChangesOperation;

public class ReplaceWithOursActionHandler extends ReplaceConflictActionHandler {

	public ReplaceWithOursActionHandler() {
		super(DiscardChangesOperation.Stage.OURS);
	}
}
