package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class OpenClosedProjectHereAction extends Action {

	private CommonViewer viewer;
	private IFolder targetFolder;
	private IProject project;

	public OpenClosedProjectHereAction(IFolder targetFolder, IProject project, CommonViewer viewer) {
		super(WorkbenchNavigatorMessages.OpenProjectHere);
		this.targetFolder = targetFolder;
		this.project = project;
		this.viewer = viewer;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(SharedImages.IMG_OBJ_PROJECT));
	}
	
	public void run() {
		try {
			project.open(new NullProgressMonitor());
			NestedProjectUtils.registerProjectShownInFolder(targetFolder, project);
			viewer.refresh(project);
			viewer.refresh(targetFolder.getParent());
			viewer.setSelection(new StructuredSelection(project));
		} catch (Exception ex) {
		}
	}
}
