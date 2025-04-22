package org.eclipse.egit.ui.internal.sharing;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class MoveProjectsLabelProvider extends BaseLabelProvider implements
		ITableLabelProvider {
	IPath targetFolder;
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

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
