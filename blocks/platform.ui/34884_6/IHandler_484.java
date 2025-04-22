
package org.eclipse.ui.commands;

import java.util.Map;

import org.eclipse.ui.menus.UIElement;

public interface IElementUpdater {
	public void updateElement(UIElement element, Map parameters);
}
