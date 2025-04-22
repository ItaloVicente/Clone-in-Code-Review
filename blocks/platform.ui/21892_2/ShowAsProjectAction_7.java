package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonViewer;

public class ShowAsFolderAction extends Action {

	private CommonViewer viewer;
	private IFolder folder;

	public ShowAsFolderAction(IFolder folder, CommonViewer viewer) {
		super(NLS.bind(WorkbenchNavigatorMessages.ShowAsFolder, folder.getName()));
		this.folder = folder;
		this.viewer = viewer;
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	}
	
	public void run() {
		NestedProjectManager.unregisterProjectShownInFolder(folder);
		viewer.refresh(folder.getParent());
		viewer.setSelection(new StructuredSelection(new Object[] { folder }));
	}
}
