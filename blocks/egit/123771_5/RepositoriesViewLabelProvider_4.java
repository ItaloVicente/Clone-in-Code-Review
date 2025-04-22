package org.eclipse.egit.ui.internal.repository;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExecutableExtensionFactory;

public class RepositoriesViewExtensionFactory
		implements IExecutableExtensionFactory {

	@Override
	public Object create() throws CoreException {
		return new RepositoriesViewContentProvider(true);
	}

}
