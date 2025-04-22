package org.eclipse.egit.ui;

import org.eclipse.egit.core.op.CommitOperation;

public interface CommitObserver {

	boolean finalizeCommit(CommitOperation commitOperation);

	String getRefuseReason();
}
