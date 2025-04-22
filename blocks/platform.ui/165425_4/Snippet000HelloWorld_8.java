package org.eclipse.core.databinding.binder;

import org.eclipse.core.databinding.UpdateValueStrategy;

public enum ValueUpdatePolicy {
	POLICY_NEVER(UpdateValueStrategy.POLICY_NEVER), //
	POLICY_ON_REQUEST(UpdateValueStrategy.POLICY_ON_REQUEST), //
	POLICY_CONVERT(UpdateValueStrategy.POLICY_CONVERT), //
	POLICY_UPDATE(UpdateValueStrategy.POLICY_UPDATE);

	private final int policy;

	private ValueUpdatePolicy(int policy) {
		this.policy = policy;
	}

	public int getPolicy() {
		return policy;
	}
}
