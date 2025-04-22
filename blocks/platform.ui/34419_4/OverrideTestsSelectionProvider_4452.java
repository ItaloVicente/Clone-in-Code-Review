package org.eclipse.ui.tests.views.properties.tabbed.override;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;

public class OverrideTestsSelection implements ISelection {

	private Element element;

	public OverrideTestsSelection(Element newElement) {
		this.element = newElement;
	}

	public Element getElement() {
		return element;
	}

	public boolean isEmpty() {
		return false;
	}

	public String toString() {
		if (getElement() == null) {
			return super.toString();
		}
		return getElement().getName();
	}

}
