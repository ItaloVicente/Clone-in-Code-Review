package org.eclipse.ui.internal.navigator.resources.nested;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;

public class NestedProjectsContentProvider implements ITreeContentProvider {

	public static final String EXTENSION_ID = "org.eclipse.ui.navigator.resources.nested.nestedProjectContentProvider"; //$NON-NLS-1$

	private Command projectPresetionCommand;

	public NestedProjectsContentProvider() {
		ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
		this.projectPresetionCommand = commandService.getCommand(ProjectPresentationHandler.COMMAND_ID);
		try {
			HandlerUtil.updateRadioState(this.projectPresetionCommand, Boolean.TRUE.toString());
		} catch (ExecutionException ex) {
			WorkbenchNavigatorPlugin.log(ex.getMessage(), new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, ex.getMessage(), ex));
		}
	}

	@Override
	public void dispose() {
		try {
			HandlerUtil.updateRadioState(this.projectPresetionCommand, Boolean.FALSE.toString());
		} catch (ExecutionException ex) {
			WorkbenchNavigatorPlugin.log(ex.getMessage(), new Status(IStatus.ERROR, WorkbenchNavigatorPlugin.PLUGIN_ID, ex.getMessage(), ex));
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (! (parentElement instanceof IContainer)) {
			return null;
		}
		IContainer container = (IContainer)parentElement;
		List<IProject> nestedProjects = new ArrayList<IProject>();
		try {
			List<IResource> children = Arrays.asList(container.members());
			for (IResource child : children) {
				if (child instanceof IFolder) {
					IProject project = NestedProjectManager.getProject((IFolder) child);
					if (project != null) {
						nestedProjects.add(project);
					}
				}
			}
			return nestedProjects.toArray(new IProject[nestedProjects.size()]);
		} catch (CoreException ex) {
			return null;
		}
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof IProject) {
			IProject project = (IProject)element;
			if (NestedProjectManager.isShownAsNested(project)) {
				return NestedProjectManager.getMostDirectOpenContainer(project);
			}
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return ((IProject)element).isOpen();
		}
		return element instanceof IContainer;
	}

}
