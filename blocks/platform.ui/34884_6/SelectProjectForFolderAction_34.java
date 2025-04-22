
package org.eclipse.ui.internal.navigator.resources.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.osgi.util.NLS;

import org.eclipse.swt.widgets.Shell;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.IShellProvider;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.BuildAction;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.actions.CloseUnrelatedProjectsAction;
import org.eclipse.ui.actions.OpenResourceAction;
import org.eclipse.ui.actions.RefreshAction;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class ResourceMgmtActionProvider extends CommonActionProvider {

	private BuildAction buildAction;

	private OpenResourceAction openProjectAction;

	private CloseResourceAction closeProjectAction;

	private CloseUnrelatedProjectsAction closeUnrelatedProjectsAction;

	private RefreshAction refreshAction;

	private Shell shell;

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);
		shell = aSite.getViewSite().getShell();
		makeActions();
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(), refreshAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.BUILD_PROJECT.getId(), buildAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.OPEN_PROJECT.getId(), openProjectAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_PROJECT.getId(), closeProjectAction);
		actionBars.setGlobalActionHandler(IDEActionFactory.CLOSE_UNRELATED_PROJECTS.getId(), closeUnrelatedProjectsAction);
		updateActionBars();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		boolean isProjectSelection = true;
		boolean hasOpenProjects = false;
		boolean hasClosedProjects = false;
		boolean hasBuilder = true; // false if any project is closed or does not
		Iterator<Object> resources = selection.iterator();

		while (resources.hasNext() && (!hasOpenProjects || !hasClosedProjects || hasBuilder || isProjectSelection)) {
			Object next = resources.next();
			IProject project = null;

			if (next instanceof IProject) {
				project = (IProject) next;
			} else if (next instanceof IAdaptable) {
				project = (IProject) ((IAdaptable) next).getAdapter(IProject.class);
			}

			if (project == null) {
				isProjectSelection = false;
				continue;
			}
			if (project.isOpen()) {
				hasOpenProjects = true;
				if (hasBuilder && !hasBuilder(project)) {
					hasBuilder = false;
				}
			} else {
				hasClosedProjects = true;
				hasBuilder = false;
			}
		}
		if (!selection.isEmpty() && isProjectSelection && !ResourcesPlugin.getWorkspace().isAutoBuilding() && hasBuilder) {
			buildAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, buildAction);
		}
		if (!hasClosedProjects) {
			refreshAction.selectionChanged(selection);
			menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, refreshAction);
		}
		if (isProjectSelection) {
			if (hasClosedProjects) {
				openProjectAction.selectionChanged(selection);
				menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, openProjectAction);
			}
			if (hasOpenProjects) {
				closeProjectAction.selectionChanged(selection);
				menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, closeProjectAction);
				closeUnrelatedProjectsAction.selectionChanged(selection);
				menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, closeUnrelatedProjectsAction);
			}
		}
	}

	boolean hasBuilder(IProject project) {
		try {
			ICommand[] commands = project.getDescription().getBuildSpec();
			if (commands.length > 0) {
				return true;
			}
		} catch (CoreException e) {
		}
		return false;
	}

	protected void makeActions() {
		IShellProvider sp = new IShellProvider() {
			@Override
			public Shell getShell() {
				return shell;
			}
		};

		openProjectAction = new OpenResourceAction(sp);

		closeProjectAction = new CloseResourceAction(sp);

		closeUnrelatedProjectsAction = new CloseUnrelatedProjectsAction(sp);

		refreshAction = new RefreshAction(sp) {
			@Override
			public void run() {
				final IStatus[] errorStatus = new IStatus[1];
				errorStatus[0] = Status.OK_STATUS;
				final WorkspaceModifyOperation op = (WorkspaceModifyOperation) createOperation(errorStatus);
				WorkspaceJob job = new WorkspaceJob("refresh") { //$NON-NLS-1$

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						try {
							op.run(monitor);
							if (shell != null && !shell.isDisposed()) {
								shell.getDisplay().asyncExec(new Runnable() {
									@Override
									public void run() {
										StructuredViewer viewer = getActionSite().getStructuredViewer();
										if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
											viewer.refresh();
										}
									}
								});
							}
						} catch (InvocationTargetException e) {
							String msg = NLS.bind(WorkbenchNavigatorMessages.ResourceMgmtActionProvider_logTitle, getClass().getName(), e.getTargetException());
							throw new CoreException(new Status(IStatus.ERROR, NavigatorPlugin.PLUGIN_ID, IStatus.ERROR, msg, e.getTargetException()));
						} catch (InterruptedException e) {
							return Status.CANCEL_STATUS;
						}
						return errorStatus[0];
					}

				};
				ISchedulingRule rule = op.getRule();
				if (rule != null) {
					job.setRule(rule);
				}
				job.setUser(true);
				job.schedule();
			}
		};
		refreshAction.setDisabledImageDescriptor(getImageDescriptor("dlcl16/refresh_nav.gif"));//$NON-NLS-1$
		refreshAction.setImageDescriptor(getImageDescriptor("elcl16/refresh_nav.gif"));//$NON-NLS-1$
		refreshAction.setActionDefinitionId(IWorkbenchCommandConstants.FILE_REFRESH);

		buildAction = new BuildAction(sp, IncrementalProjectBuilder.INCREMENTAL_BUILD);
		buildAction.setActionDefinitionId(IWorkbenchCommandConstants.PROJECT_BUILD_PROJECT);
	}

	protected ImageDescriptor getImageDescriptor(String relativePath) {
		return IDEWorkbenchPlugin.getIDEImageDescriptor(relativePath);

	}

	@Override
	public void updateActionBars() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		refreshAction.selectionChanged(selection);
		buildAction.selectionChanged(selection);
		openProjectAction.selectionChanged(selection);
		closeUnrelatedProjectsAction.selectionChanged(selection);
		closeProjectAction.selectionChanged(selection);
	}

}
