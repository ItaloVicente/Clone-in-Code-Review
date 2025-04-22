package org.eclipse.egit.core.mergestrategy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.jgit.merge.MergeStrategy;

public class TheirsMergeStrategyExtensionFactory implements
		IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {
		return MergeStrategy.THEIRS;
	}
}
