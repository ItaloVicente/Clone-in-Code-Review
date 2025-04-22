package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertySource;

public class ButtonElement
	implements IWorkbenchAdapter, IAdaptable {

	private String headingName;

	private Button ctl;

	public ButtonElement(Button initBtn, String heading) {
		this.headingName = heading;
		this.ctl = initBtn;
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IWorkbenchAdapter.class)
			return this;
		if (adapter == IPropertySource.class)
			return new ButtonElementProperties(this);
		return null;
	}

	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	public String getLabel(Object o) {
		return headingName;
	}

	public Object getParent(Object o) {
		return null;
	}

	public Button getControl() {
		return ctl;
	}

	public Object[] getChildren(Object o) {
		return null;
	}

}
