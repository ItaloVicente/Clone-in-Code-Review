package org.eclipse.ui.examples.views.properties.tabbed.logic.properties;

import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class LogicElementTypeMapper
	implements ITypeMapper {

	public Class mapType(Object object) {
		Class type = object.getClass();
		if (object instanceof EditPart) {
			type = ((EditPart) object).getModel().getClass();
		}
		return type;
	}
}
