package org.eclipse.egit.core.mergestrategy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.jgit.merge.MergeStrategy;

public class MergeStrategyExtensionFactory implements
		IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {
		return MergeStrategy.OURS;
	}
}
