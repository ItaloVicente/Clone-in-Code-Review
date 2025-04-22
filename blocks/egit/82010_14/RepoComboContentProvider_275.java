package org.eclipse.egit.ui.internal.sharing;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;

public class MoveProjectsLabelProvider extends BaseLabelProvider implements
		ITableLabelProvider {
	IPath targetFolder;

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0)
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(SharedImages.IMG_OBJ_PROJECT);
		else
			return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		IProject prj = (IProject) element;
		switch (columnIndex) {
		case 0:
			return prj.getName();
		case 1:
			return prj.getLocation().toString();
		case 2:
			if (targetFolder != null)
				return targetFolder.append(prj.getName()).toString();
			return null;
		default:
			return null;
		}
	}
}
