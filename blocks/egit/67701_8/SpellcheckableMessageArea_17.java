package org.eclipse.egit.ui.internal;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

public class RepositoryCacheRule implements ISchedulingRule {

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof RepositoryCacheRule) {
			return true;
		} else if (rule instanceof MultiRule) {
			for (ISchedulingRule child : ((MultiRule) rule).getChildren()) {
				if (!contains(child)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof RepositoryCacheRule) {
			return true;
		} else if (rule instanceof MultiRule) {
			for (ISchedulingRule child : ((MultiRule) rule).getChildren()) {
				if (isConflicting(child)) {
					return true;
				}
			}
		}
		return false;
	}

}
