package org.eclipse.egit.gitflow.op;

enum EGitFlowOperation {
	START("start"), //$NON-NLS-1$
	PUBLISH("publish"), //$NON-NLS-1$
	TRACK("track"), //$NON-NLS-1$
	FINISH("finish"); //$NON-NLS-1$

	private final String id;

	private EGitFlowOperation(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}
