package org.eclipse.ui.navigator.resources;

import org.eclipse.osgi.util.NLS;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.common.CommandException;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.IAggregateWorkingSet;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.navigator.framelist.Frame;
import org.eclipse.ui.internal.navigator.framelist.FrameList;
import org.eclipse.ui.internal.navigator.framelist.TreeFrame;
import org.eclipse.ui.internal.navigator.resources.ResourceToItemsMapper;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.INavigatorContentService;


public final class ProjectExplorer extends CommonNavigator {

	public static final String VIEW_ID = IPageLayout.ID_PROJECT_EXPLORER;

	public static final int WORKING_SETS = 0;

	public static final int PROJECTS = 1;

	private int rootMode;

	private String workingSetLabel;

	@Override
	public void createPartControl(Composite aParent) {
		super.createPartControl(aParent);
		
		if (!false)
			getCommonViewer().setMapper(new ResourceToItemsMapper(getCommonViewer()));
	}	
	
	@Override
	public void updateTitle() {
		super.updateTitle();
		Object input = getCommonViewer().getInput();

		if (input == null || input instanceof IAggregateWorkingSet) {
			setContentDescription(""); //$NON-NLS-1$
			return;
		}

		if (!(input instanceof IResource)) {
			String label = ((ILabelProvider) getCommonViewer().getLabelProvider()).getText(input);
			if (label != null) {
				setContentDescription(label);
				return;
			}
			if (input instanceof IAdaptable) {
				IWorkbenchAdapter wbadapter = (IWorkbenchAdapter) ((IAdaptable) input)
						.getAdapter(IWorkbenchAdapter.class);
				if (wbadapter != null) {
					setContentDescription(wbadapter.getLabel(input));
					return;
				}
			}
			setContentDescription(input.toString());
			return;
		}

		IResource res = (IResource) input;
		setContentDescription(res.getName());
	}

	@Override
	public String getFrameToolTipText(Object element) {
		String result;
		if (!(element instanceof IResource)) {
			if (element instanceof IAggregateWorkingSet) {
				result = WorkbenchNavigatorMessages.ProjectExplorerPart_workingSetModel;
			} else if (element instanceof IWorkingSet) {
				result = ((IWorkingSet) element).getLabel();
			} else {
				result = super.getFrameToolTipText(element);
			}
		} else {
			IPath path = ((IResource) element).getFullPath();
			if (path.isRoot()) {
				result = WorkbenchNavigatorMessages.ProjectExplorerPart_workspace;
			} else {
				result = path.makeRelative().toString();
			}
		}

		if (rootMode == PROJECTS) {
			if (workingSetLabel == null)
				return result;
			if (result.length() == 0)
				return NLS.bind(WorkbenchNavigatorMessages.ProjectExplorer_toolTip,
						new String[] { workingSetLabel });
			return NLS.bind(WorkbenchNavigatorMessages.ProjectExplorer_toolTip2, new String[] {
					result, workingSetLabel });
		}

		if (element != null && !(element instanceof IWorkingSet)
				&& getCommonViewer() != null) {
			FrameList frameList = getCommonViewer().getFrameList();
			if (frameList == null)
				return result;
			int index = frameList.getCurrentIndex();
			IWorkingSet ws = null;
			while (index >= 0) {
				Frame frame = frameList.getFrame(index);
				if (frame instanceof TreeFrame) {
					Object input = ((TreeFrame) frame).getInput();
					if (input instanceof IWorkingSet && !(input instanceof IAggregateWorkingSet)) {
						ws = (IWorkingSet) input;
						break;
					}
				}
				index--;
			}
			if (ws != null) {
				return NLS.bind(WorkbenchNavigatorMessages.ProjectExplorer_toolTip3,
						new String[] { ws.getLabel(), result });
			}
			return result;
		}
		return result;

	}

	@Override
	public void setRootMode(int mode) {
		rootMode = mode;
	}

	@Override
	public int getRootMode() {
		return rootMode;
	}

	@Override
	public void setWorkingSetLabel(String label) {
		workingSetLabel = label;
	}

	@Override
	public String getWorkingSetLabel() {
		return workingSetLabel;
	}

	@Override
	protected void handleDoubleClick(DoubleClickEvent anEvent) {
		ICommandService commandService = getViewSite().getService(ICommandService.class);
		Command openProjectCommand = commandService.getCommand(IWorkbenchCommandConstants.PROJECT_OPEN_PROJECT);
		if (openProjectCommand != null && openProjectCommand.isHandled()) {
			IStructuredSelection selection = (IStructuredSelection) anEvent
					.getSelection();
			Object element = selection.getFirstElement();
			if (element instanceof IProject && !((IProject) element).isOpen()) {
				try {
					openProjectCommand.executeWithChecks(new ExecutionEvent());
				} catch (CommandException ex) {
					IStatus status = WorkbenchNavigatorPlugin.createErrorStatus("'Open Project' failed", ex); //$NON-NLS-1$
					WorkbenchNavigatorPlugin.getDefault().getLog().log(status);
				}
				return;
			}
		}
		super.handleDoubleClick(anEvent);
	}

}
