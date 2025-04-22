package org.eclipse.ui.internal;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class ViewerActionBuilder extends PluginActionBuilder {
    

    private ISelectionProvider provider;

    private IWorkbenchPart part;

    public ViewerActionBuilder() {
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            IConfigurationElement element) {
        if (part instanceof IViewPart) {
			return new ActionDescriptor(element, ActionDescriptor.T_VIEW, part);
		}
		return new ActionDescriptor(element, ActionDescriptor.T_EDITOR, part);
    }

    @Override
	protected BasicContribution createContribution() {
        return new ViewerContribution(provider);
    }

    public void dispose() {
        if (cache != null) {
            for (int i = 0; i < cache.size(); i++) {
                ((BasicContribution) cache.get(i)).dispose();
            }
            cache = null;
        }
    }

    @Override
	protected boolean readElement(IConfigurationElement element) {
        String tag = element.getName();

        if (currentContribution != null && tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
            ((ViewerContribution) currentContribution)
                    .setVisibilityTest(element);
            return true;
        }

        return super.readElement(element);
    }

    public boolean readViewerContributions(String id, ISelectionProvider prov,
            IWorkbenchPart part) {
		Assert.isTrue(part instanceof IViewPart || part instanceof IEditorPart);
        provider = prov;
        this.part = part;
        readContributions(id, IWorkbenchRegistryConstants.TAG_VIEWER_CONTRIBUTION,
                IWorkbenchRegistryConstants.PL_POPUP_MENU);
        return (cache != null);
    }

    private static class ViewerContribution extends BasicContribution implements ISelectionChangedListener {
        private ISelectionProvider selProvider;

        private ActionExpression visibilityTest;

        public ViewerContribution(ISelectionProvider selProvider) {
            super();
            this.selProvider = selProvider;
			if (selProvider != null) {
				selProvider.addSelectionChangedListener(this);
			}
        }

        public void setVisibilityTest(IConfigurationElement element) {
            visibilityTest = new ActionExpression(element);
        }

        @Override
		public void contribute(IMenuManager menu, boolean menuAppendIfMissing,
                IToolBarManager toolbar, boolean toolAppendIfMissing) {
            boolean visible = true;

            if (visibilityTest != null) {
                ISelection selection = selProvider.getSelection();
                if (selection instanceof IStructuredSelection) {
                    visible = visibilityTest
                            .isEnabledFor((IStructuredSelection) selection);
                } else {
                    visible = visibilityTest.isEnabledFor(selection);
                }
            }

            if (visible) {
				super.contribute(menu, menuAppendIfMissing, toolbar,
                        toolAppendIfMissing);
			}
        }
		
		@Override
		public void dispose() {
			if (selProvider != null) {
				selProvider.removeSelectionChangedListener(this);
			}
			disposeActions();
			super.dispose();
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (actions != null) {
				if (actions != null) {
					for (int i = 0; i < actions.size(); i++) {
						PluginAction proxy = ((ActionDescriptor) actions.get(i))
								.getAction();
						proxy.selectionChanged(event);
					}
				}
			}
		}
    }

}
