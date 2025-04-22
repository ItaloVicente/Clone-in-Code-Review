package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class ShowAsProjectAction extends Action {

	private CommonViewer viewer;
	private IProject project;
	private IFolder targetFolder;

	public ShowAsProjectAction(IProject project, IFolder targetFolder, CommonViewer viewer) {
		super(NLS.bind(WorkbenchNavigatorMessages.ShowProjectHere, project.getName()));
		this.project = project;
		this.targetFolder = targetFolder;
		this.viewer = viewer;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(SharedImages.IMG_OBJ_PROJECT));
	}
	
	public void run() {
		NestedProjectManager.registerProjectShownInFolder(targetFolder, project);
		viewer.refresh(project);
		viewer.refresh(targetFolder.getParent());
	}
}
