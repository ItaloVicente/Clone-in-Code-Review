package org.eclipse.ui.examples.jobs;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class TestJobRule implements ISchedulingRule {
	private int jobOrder;

	public TestJobRule(int order) {
		jobOrder = order;
	}

	public boolean contains(ISchedulingRule rule) {
		if (rule instanceof IResource || rule instanceof TestJobRule)
			return true;
		return false;
	}

	public boolean isConflicting(ISchedulingRule rule) {
		if (!(rule instanceof TestJobRule))
			return false;
		return ((TestJobRule) rule).getJobOrder() >= jobOrder;
	}

	public int getJobOrder() {
		return jobOrder;
	}

}
