package org.eclipse.ui.internal.wizards.datatransfer;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;

public class SubdirectoryOrSameNameSchedulingRule implements ISchedulingRule {

	private File directory;

	public SubdirectoryOrSameNameSchedulingRule(File directory) {
		this.directory = directory;
	}

	public SubdirectoryOrSameNameSchedulingRule(IResource resource) {
		this(resource.getLocation().toFile());
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		if (rule == this || rule instanceof IResource) {
			return true;
		} else if (rule instanceof MultiRule) {
			for (ISchedulingRule child : ((MultiRule)rule).getChildren()) {
				if (this.contains(child)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		if (rule instanceof SubdirectoryOrSameNameSchedulingRule) {
			SubdirectoryOrSameNameSchedulingRule otherRule = (SubdirectoryOrSameNameSchedulingRule)rule;
			return
				otherRule.directory.getAbsolutePath().startsWith(this.directory.getAbsolutePath()) ||
				otherRule.directory.getName().equals(this.directory.getName());
		} else if (rule instanceof IResource) {
			return true;
		}  else if (rule instanceof MultiRule) {
			for (ISchedulingRule child : ((MultiRule)rule).getChildren()) {
				if (this.isConflicting(child)) {
					return true;
				}
			}
		}
		return false;
				
	}

}
