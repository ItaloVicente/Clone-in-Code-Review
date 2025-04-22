package org.eclipse.ui.tests.views.properties.tabbed.override;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;

public class OverrideTestsLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public String getColumnText(Object obj, int index) {
		return getText(obj);
	}

	public Image getImage(Object object) {
		if (object instanceof Element) {
			Element element = (Element) object;
			return element.getImage();
		}
		return super.getImage(object);
	}

	public String getText(Object object) {
		if (object instanceof Element) {
			Element element = (Element) object;
			return element.getName();
		}
		return super.getText(object);
	}
}
