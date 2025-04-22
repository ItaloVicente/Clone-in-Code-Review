package org.eclipse.ui.tests.navigator.jst;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Image;

public interface ICompressedNode   {
	public Image getImage();

	public String getLabel();

	public Object[] getChildren(ITreeContentProvider delegateContentProvider);

}
