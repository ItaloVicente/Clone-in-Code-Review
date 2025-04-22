package org.eclipse.egit.ui.internal.repository.tree.filter;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IExecutableExtensionFactory;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;

public class NodeFilterFactory
		implements IExecutableExtensionFactory, IExecutableExtension {

	private RepositoryTreeNodeType typeToHide;

	@Override
	public Object create() throws CoreException {
		return new NodeByTypeFilter(typeToHide);
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		typeToHide = RepositoryTreeNodeType.valueOf((String) data);
	}

}
