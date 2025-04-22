
package org.eclipse.ui.internal.navigator.resources.actions;

import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.internal.resources.ProjectDescriptionReader;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class OpenFolderAsProjectAction extends Action {
	
	private IFolder folder;
	private CommonViewer viewer;

	public OpenFolderAsProjectAction(IFolder folder, CommonViewer viewer) {
		super(WorkbenchNavigatorMessages.OpenProjectAction_OpenExistingProject);
		this.folder = folder;
		this.viewer = viewer;
		setDescription(WorkbenchNavigatorMessages.OpenProjectAction_OpenExistingProject_desc);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(SharedImages.IMG_OBJ_PROJECT));
	}

	public void run() {
		try {
			IProjectDescription desc = new ProjectDescriptionReader().read(folder.getLocation().append(IProjectDescription.DESCRIPTION_FILE_NAME));
			desc.setLocation(folder.getLocation());
			CreateProjectOperation operation = new CreateProjectOperation(desc, desc.getName());
			OperationHistoryFactory.getOperationHistory().execute(operation, null, null);
			viewer.setSelection(new StructuredSelection(operation.getAffectedObjects()));
		} catch (Exception ex) {
		}
	}
}
