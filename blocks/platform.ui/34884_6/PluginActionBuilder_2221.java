package org.eclipse.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IActionDelegateWithEvent;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.SelectionEnabler;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.internal.util.Util;


public abstract class PluginAction extends Action implements
        ISelectionListener, ISelectionChangedListener, INullSelectionListener,
        IPluginContribution {
    private IActionDelegate delegate;

    private SelectionEnabler enabler;

    private ISelection selection;

    private IConfigurationElement configElement;

    private String pluginId;

    private String runAttribute = IWorkbenchRegistryConstants.ATT_CLASS;

    private static int actionCount = 0;

    public PluginAction(IConfigurationElement actionElement, String id,
            int style) {
        super(null, style);

        this.configElement = actionElement;

        if (id != null) {
            setId(id);
        } else {
            setId("PluginAction." + Integer.toString(actionCount)); //$NON-NLS-1$
            ++actionCount;
        }

        String defId = actionElement
                .getAttribute(IWorkbenchRegistryConstants.ATT_DEFINITION_ID);
        setActionDefinitionId(defId);

        pluginId = configElement.getNamespace();

        if (configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ENABLES_FOR) != null) {
            enabler = new SelectionEnabler(configElement);
        } else {
			IConfigurationElement[] kids = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_ENABLEMENT);
			IConfigurationElement[] kids2 = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_SELECTION);
			if (kids.length > 0 || kids2.length>0) {
				enabler = new SelectionEnabler(configElement);
			}
		}

        selectionChanged(StructuredSelection.EMPTY);
    }

    protected final void createDelegate() {
        if (delegate == null && runAttribute != null) {
            try {
                Object obj = WorkbenchPlugin.createExtension(configElement,
                        runAttribute);
                delegate = validateDelegate(obj);
                initDelegate();
                refreshEnablement();
            } catch (Throwable e) {
                runAttribute = null;
                IStatus status = null;
                if (e instanceof CoreException) {
                    status = ((CoreException) e).getStatus();
                } else {
                    status = StatusUtil
                            .newStatus(
                                    IStatus.ERROR,
                                    "Internal plug-in action delegate error on creation.", e); //$NON-NLS-1$
                }
                String id = configElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
                WorkbenchPlugin
                        .log(
                                "Could not create action delegate for id: " + id, status); //$NON-NLS-1$
                return;
            }
        }
    }

    protected IActionDelegate validateDelegate(Object obj)
            throws WorkbenchException {
        if (obj instanceof IActionDelegate) {
			return (IActionDelegate) obj;
		}
        
        throw new WorkbenchException(
                "Action must implement IActionDelegate"); //$NON-NLS-1$
    }

    protected void initDelegate() {
        if (delegate instanceof IActionDelegate2) {
			((IActionDelegate2) delegate).init(this);
		}
    }

    protected IActionDelegate getDelegate() {
        return delegate;
    }

    protected boolean isOkToCreateDelegate() {
        String bundleId = configElement.getContributor().getName();
        return BundleUtility.isActive(bundleId);
    }

    protected void refreshEnablement() {
        if (enabler != null) {
            setEnabled(enabler.isEnabledForSelection(selection));
        }
        if (delegate != null) {
            delegate.selectionChanged(this, selection);
        }
    }

    @Override
	public void run() {
        runWithEvent(null);
    }

    @Override
	public void runWithEvent(Event event) {
        if (delegate == null) {
            createDelegate();
            if (delegate == null) {
                MessageDialog
                        .openInformation(
                                Util.getShellToParentOn(),
                                WorkbenchMessages.Information, 
                                WorkbenchMessages.PluginAction_operationNotAvailableMessage); 
                return;
            }
            if (!isEnabled()) {
                MessageDialog.openInformation(Util.getShellToParentOn(), WorkbenchMessages.Information, 
                        WorkbenchMessages.PluginAction_disabledMessage); 
                return;
            }
        }

        if (event != null) {
            if (delegate instanceof IActionDelegate2) {
                ((IActionDelegate2) delegate).runWithEvent(this, event);
                return;
            }
            if (delegate instanceof IActionDelegateWithEvent) {
                ((IActionDelegateWithEvent) delegate).runWithEvent(this, event);
                return;
            }
        }

        delegate.run(this);
    }

    public void selectionChanged(ISelection newSelection) {
        selection = newSelection;
        if (selection == null) {
			selection = StructuredSelection.EMPTY;
		}

        
        if (delegate == null && isOkToCreateDelegate()) {
			createDelegate();
		} else {
			refreshEnablement();
		}
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        ISelection sel = event.getSelection();
        selectionChanged(sel);
    }

    @Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
        selectionChanged(sel);
    }

    public ISelection getSelection() {
    	return selection;
    }

    public String getOverrideActionId() {
        return null;
    }

    protected IConfigurationElement getConfigElement() {
        return configElement;
    }

    @Override
	public String getLocalId() {
        return getId();
    }

    @Override
	public String getPluginId() {
        return pluginId;
    }

    public void disposeDelegate() {
        if (getDelegate() instanceof IActionDelegate2) {
            ((IActionDelegate2) getDelegate()).dispose();
        }
        else if (getDelegate() instanceof IWorkbenchWindowActionDelegate) {
            ((IWorkbenchWindowActionDelegate) getDelegate()).dispose();
        }
        delegate = null;
    }

    public void dispose() {
        disposeDelegate();
        selection = null;
    }
    
    @Override
	public IMenuCreator getMenuCreator() {
    	if (getDelegate()==null) {
    		createDelegate();
    	}
    	return super.getMenuCreator();
    }
}
