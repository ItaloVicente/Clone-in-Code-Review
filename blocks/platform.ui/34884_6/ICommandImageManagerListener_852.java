
package org.eclipse.ui.internal.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.commands.IElementReference;
import org.eclipse.ui.menus.UIElement;

public class ElementReference implements IElementReference {

	private String commandId;
	private UIElement element;
	private HashMap parameters;

	public ElementReference(String id, UIElement adapt, Map parms) {
		commandId = id;
		element = adapt;
		if (parms == null) {
			parameters = new HashMap();
		} else {
			parameters = new HashMap(parms);
		}
	}

	@Override
	public UIElement getElement() {
		return element;
	}

	@Override
	public String getCommandId() {
		return commandId;
	}

	@Override
	public Map getParameters() {
		return parameters;
	}
}
