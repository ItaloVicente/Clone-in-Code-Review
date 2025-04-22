package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.IWizardDescriptor;

public abstract class WorkbenchWizardNode implements IWizardNode,
        IPluginContribution {
    protected WorkbenchWizardSelectionPage parentWizardPage;

    protected IWizard wizard;

    protected IWizardDescriptor wizardElement;

    public WorkbenchWizardNode(WorkbenchWizardSelectionPage aWizardPage,
    		IWizardDescriptor element) {
        super();
        this.parentWizardPage = aWizardPage;
        this.wizardElement = element;
    }

    public abstract IWorkbenchWizard createWizard() throws CoreException;

    @Override
	public void dispose() {
    }

    protected IStructuredSelection getCurrentResourceSelection() {
        return parentWizardPage.getCurrentResourceSelection();
    }

    @Override
	public Point getExtent() {
        return new Point(-1, -1);
    }

    @Override
	public String getLocalId() {
    	IPluginContribution contribution = (IPluginContribution) Util.getAdapter(wizardElement,
				IPluginContribution.class);
		if (contribution != null) {
			return contribution.getLocalId();
		}
		return wizardElement.getId();
    }

    @Override
	public String getPluginId() {
       	IPluginContribution contribution = (IPluginContribution) Util.getAdapter(wizardElement,
				IPluginContribution.class);
		if (contribution != null) {
			return contribution.getPluginId();
		}
		return null;
    }

    @Override
	public IWizard getWizard() {
        if (wizard != null) {
			return wizard; // we've already created it
		}

        final IWorkbenchWizard[] workbenchWizard = new IWorkbenchWizard[1];
        final IStatus statuses[] = new IStatus[1];
        BusyIndicator.showWhile(parentWizardPage.getShell().getDisplay(),
                new Runnable() {
                    @Override
					public void run() {
                        SafeRunner.run(new SafeRunnable() {
                            @Override
							public void handleException(Throwable e) {
                               	IPluginContribution contribution = (IPluginContribution) Util.getAdapter(wizardElement, IPluginContribution.class);
                                statuses[0] = new Status(
                                        IStatus.ERROR,
                                        contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH,
                                        IStatus.OK,
                                        WorkbenchMessages.WorkbenchWizard_errorMessage,
                                        e);
                            }

                            @Override
							public void run() {
                                try {
                                    workbenchWizard[0] = createWizard();
                                } catch (CoreException e) {
                                	IPluginContribution contribution = (IPluginContribution) Util.getAdapter(wizardElement, IPluginContribution.class);
                                	statuses[0] = new Status(
                                            IStatus.ERROR,
                                            contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH,
                                            IStatus.OK,
                                            WorkbenchMessages.WorkbenchWizard_errorMessage,
                                            e);
                                }
                            }
                        });
                    }
                });

        if (statuses[0] != null) {
            parentWizardPage
					.setErrorMessage(WorkbenchMessages.WorkbenchWizard_errorMessage);
			StatusAdapter statusAdapter = new StatusAdapter(statuses[0]);
			statusAdapter.addAdapter(Shell.class, parentWizardPage.getShell());
			statusAdapter.setProperty(StatusAdapter.TITLE_PROPERTY,
					WorkbenchMessages.WorkbenchWizard_errorTitle);
			StatusManager.getManager()
					.handle(statusAdapter, StatusManager.SHOW);
			return null;
        }

        IStructuredSelection currentSelection = getCurrentResourceSelection();

        currentSelection = wizardElement.adaptedSelection(currentSelection);

        workbenchWizard[0].init(getWorkbench(), currentSelection);

        wizard = workbenchWizard[0];
        return wizard;
    }

    public IWizardDescriptor getWizardElement() {
        return wizardElement;
    }

    protected IWorkbench getWorkbench() {
        return parentWizardPage.getWorkbench();
    }

    @Override
	public boolean isContentCreated() {
        return wizard != null;
    }
}
