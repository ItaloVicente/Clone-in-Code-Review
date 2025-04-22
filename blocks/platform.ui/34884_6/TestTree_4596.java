package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.tests.viewers.TestLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestTableTreeLabelProvider extends TestLabelProvider implements
		ITableLabelProvider {
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return getImage();
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element != null)
			return element.toString() + " column " + columnIndex;
		return null;
	}
}
