package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.examples.logicdesigner.model.LogicElement;
import org.eclipse.gef.examples.logicdesigner.model.LogicSubpart;
import org.eclipse.jface.viewers.IFilter;

public class LogicElementSizeFilter
	implements IFilter {

	public boolean select(Object object) {
		if (object instanceof EditPart) {
			LogicElement element = (LogicElement) ((EditPart) object)
				.getModel();
			if (element instanceof LogicSubpart) {
				Dimension dimension = ((LogicSubpart) element).getSize();
				if (dimension.width != -1 && dimension.height != -1) {
					return true;
				}
			}
		}
		return false;
	}
}
