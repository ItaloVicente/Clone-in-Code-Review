package org.eclipse.ui;

import org.eclipse.ui.services.IServiceLocator;

@Deprecated
public interface INestableKeyBindingService extends IKeyBindingService {

    public boolean activateKeyBindingService(IWorkbenchSite nestedSite);

    public IKeyBindingService getKeyBindingService(IWorkbenchSite nestedSite);

    public boolean removeKeyBindingService(IWorkbenchSite nestedSite);

}
