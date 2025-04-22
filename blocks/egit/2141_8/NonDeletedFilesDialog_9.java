package org.eclipse.egit.ui.internal.dialogs;

import org.eclipse.egit.ui.internal.dialogs.FileTreeContentProvider.Node;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

public class FileTreeLabelProvider extends BaseLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		return ((Node) element).getImage();
	}

	public String getText(Object element) {
		return ((Node) element).getName();
	}
}
