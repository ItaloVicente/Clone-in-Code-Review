package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.examples.logicdesigner.model.LogicElement;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;

public abstract class AbstractSection
	extends AbstractPropertySection implements PropertyChangeListener {

	private LogicElement element;

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		Object input = ((IStructuredSelection)selection).getFirstElement();
		Assert.isTrue(input instanceof EditPart);
		Object model = ((EditPart) input).getModel();
		Assert.isTrue(model instanceof LogicElement);
		this.element = (LogicElement) model;
	}

	public void aboutToBeShown() {
		getElement().addPropertyChangeListener(this);
	}

	public void aboutToBeHidden() {
		getElement().removePropertyChangeListener(this);
	}

	public LogicElement getElement() {
		return element;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}
}
