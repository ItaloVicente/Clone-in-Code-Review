package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

public class ObjectPluginAction extends PluginAction implements IPartListener2 {
	public static final String ATT_OVERRIDE_ACTION_ID = "overrideActionId";//$NON-NLS-1$

    private String overrideActionId;

    private IWorkbenchPart activePart;
    
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		if (activePart != null && partRef.getPart(false) == activePart) {
			selectionChanged(StructuredSelection.EMPTY);
			disposeDelegate();
			activePart = null;
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
	}

    public ObjectPluginAction(IConfigurationElement actionElement, String id,
            int style) {
        super(actionElement, id, style);
        overrideActionId = actionElement.getAttribute(ATT_OVERRIDE_ACTION_ID);
    }

    @Override
	protected void initDelegate() {
        super.initDelegate();
		final IActionDelegate actionDelegate = getDelegate();
		if (actionDelegate instanceof IObjectActionDelegate
				&& activePart != null) {
			final IObjectActionDelegate objectActionDelegate = (IObjectActionDelegate) actionDelegate;
			final ISafeRunnable runnable = new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					objectActionDelegate.setActivePart(ObjectPluginAction.this,
							activePart);
				}

				@Override
				public void handleException(Throwable exception) {
				}
			};
			SafeRunner.run(runnable);
		}
	}

    public void setActivePart(IWorkbenchPart targetPart) {
    	if (activePart != targetPart) {
			if (activePart != null) {
				activePart.getSite().getPage().removePartListener(this);
			}
			if (targetPart != null) {
				targetPart.getSite().getPage().addPartListener(this);
			}
		}
        activePart = targetPart;
        IActionDelegate delegate = getDelegate();
        if (delegate instanceof IObjectActionDelegate && activePart != null) {
			final IObjectActionDelegate objectActionDelegate = (IObjectActionDelegate) delegate;
			final ISafeRunnable runnable = new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					objectActionDelegate.setActivePart(ObjectPluginAction.this,
							activePart);
				}

				@Override
				public void handleException(Throwable exception) {
				}
			};
			SafeRunner.run(runnable);
		}
	}

    @Override
	public String getOverrideActionId() {
        return overrideActionId;
    }
    
    @Override
	public void dispose() {
    	if (activePart!=null) {
    		activePart.getSite().getPage().removePartListener(this);
    		activePart = null;
    	}
    	super.dispose();
    }
}
