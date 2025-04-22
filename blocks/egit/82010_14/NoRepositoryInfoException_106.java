package org.eclipse.egit.ui.internal.provisional.wizards;

import java.util.Collection;

public interface IRepositoryServerProvider {

	public Collection<RepositoryServerInfo> getRepositoryServerInfos() throws NoRepositoryServerInfoException;

}
