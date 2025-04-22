package org.eclipse.egit.ui.internal.actions;

import org.eclipse.egit.core.op.DiscardChangesOperation;

public class ReplaceWithTheirsActionHandler extends ReplaceConflictActionHandler {

	public ReplaceWithTheirsActionHandler() {
		super(DiscardChangesOperation.Stage.THEIRS);
	}
}
