package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.WorkbenchException;

public final class ViewPluginAction extends PartPluginAction {
    private IViewPart viewPart;

    public ViewPluginAction(IConfigurationElement actionElement,
            IViewPart viewPart, String id, int style) {
        super(actionElement, id, style);
        this.viewPart = viewPart;
        registerSelectionListener(viewPart);
    }

    @Override
	protected IActionDelegate validateDelegate(Object obj)
            throws WorkbenchException {
        if (obj instanceof IViewActionDelegate) {
			return (IViewActionDelegate) obj;
		} else {
			throw new WorkbenchException(
                    "Action must implement IViewActionDelegate"); //$NON-NLS-1$
		}
    }

    @Override
	protected void initDelegate() {
        super.initDelegate();
        ((IViewActionDelegate) getDelegate()).init(viewPart);
    }

    @Override
	public boolean isOkToCreateDelegate() {
        return super.isOkToCreateDelegate() && viewPart != null;
    }
	
	@Override
	public void dispose() {
		unregisterSelectionListener(viewPart);
		super.dispose();
	}
}
