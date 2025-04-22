package org.eclipse.egit.ui;

public interface CommitObserver {

	boolean finaliceCommit(org.eclipse.egit.core.op.CommitOperation commitOperation);

	String getRefuseReason();
}
