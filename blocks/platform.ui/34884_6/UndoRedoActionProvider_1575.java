
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class SelectProjectForFolderAction extends Action {

	private IProject project;
	private CommonViewer viewer;

	public SelectProjectForFolderAction(IProject project, CommonViewer viewer) {
		super(NLS.bind(WorkbenchNavigatorMessages.SelectProjectForFolderAction_SelectProject, project.getName()));
		this.project = project;
		this.viewer = viewer;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(SharedImages.IMG_OBJ_PROJECT));
	}
	
	@Override
	public void run() {
		viewer.setSelection(new StructuredSelection( new Object[] { this.project } ));
	}

}
