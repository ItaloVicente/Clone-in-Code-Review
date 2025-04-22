package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import java.util.Iterator;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.examples.logicdesigner.model.Circuit;
import org.eclipse.gef.examples.logicdesigner.model.LogicElement;
import org.eclipse.gef.examples.logicdesigner.model.LogicSubpart;
import org.eclipse.gef.examples.logicdesigner.model.Wire;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class LogicElementLabelProvider
	extends LabelProvider {

	private ITypeMapper typeMapper;

	public LogicElementLabelProvider() {
		super();
		typeMapper = new LogicElementTypeMapper();
	}

	public Image getImage(Object objects) {
		if (objects == null || objects.equals(StructuredSelection.EMPTY)) {
			return null;
		}
		final boolean multiple[] = {false};
		Object object = getObject(objects, multiple);
		if (object == null) {
			ImageDescriptor imageDescriptor = ImageDescriptor.createFromFile(
				Circuit.class, "icons/comp.gif");//$NON-NLS-1$
			return imageDescriptor.createImage();
		} else {
			if (!(object instanceof EditPart)) {
				return null;
			}
			LogicElement element = (LogicElement) ((EditPart) object)
				.getModel();
			if (element instanceof Wire) {
				ImageDescriptor imageDescriptor = ImageDescriptor
					.createFromFile(Circuit.class, "icons/connection16.gif");//$NON-NLS-1$
				return imageDescriptor.createImage();
			}
			return ((LogicSubpart) element).getIconImage();
		}
	}

	public String getText(Object objects) {
		if (objects == null || objects.equals(StructuredSelection.EMPTY)) {
			return "No items selected";//$NON-NLS-1$
		}
		final boolean multiple[] = {false};
		final Object object = getObject(objects, multiple);
		if (object == null || ((IStructuredSelection) objects).size() > 1) {
			return ((IStructuredSelection) objects).size() + " items selected";//$NON-NLS-1$
		} else {
			String name = typeMapper.mapType(object).getName();
			return name.substring(name.lastIndexOf('.') + 1);
		}
	}

	private Object getObject(Object objects, boolean multiple[]) {
		Assert.isNotNull(objects);
		Object object = null;
		if (objects instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) objects;
			object = selection.getFirstElement();
			if (selection.size() == 1) {
				multiple[0] = false;
				return object;
			}
			multiple[0] = true;
			Class firstClass = typeMapper.mapType(object);
			if (selection.size() > 1) {
				for (Iterator i = selection.iterator(); i.hasNext();) {
					Object next = i.next();
					Class nextClass = typeMapper.mapType(next);
					if (!nextClass.equals(firstClass)) {
						multiple[0] = false;
						object = null;
						break;
					}
				}
			}
		} else {
			multiple[0] = false;
			object = objects;
		}
		return object;
	}

}
