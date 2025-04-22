
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuSeparator;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;

public class OpaqueElementUtil {

	private static final String OPAQUE_TAG = "Opaque"; //$NON-NLS-1$

	private static final String OPAQUE_ITEM_KEY = "OpaqueItem"; //$NON-NLS-1$

	public static Object clearOpaqueItem(MUIElement uiElement) {
		return uiElement.getTransientData().remove(OPAQUE_ITEM_KEY);
	}

	public static MMenu createOpaqueMenu() {
		final MMenu menu = MMenuFactory.INSTANCE.createMenu();
		menu.getTags().add(OPAQUE_TAG);
		return menu;
	}

	public static MMenuItem createOpaqueMenuItem() {
		final MMenuItem item = MMenuFactory.INSTANCE.createDirectMenuItem();
		item.getTags().add(OPAQUE_TAG);
		return item;
	}

	public static MMenuSeparator createOpaqueMenuSeparator() {
		final MMenuSeparator separator = MMenuFactory.INSTANCE.createMenuSeparator();
		separator.getTags().add(OPAQUE_TAG);
		return separator;
	}

	public static MToolItem createOpaqueToolItem() {
		final MToolItem item = MMenuFactory.INSTANCE.createDirectToolItem();
		item.getTags().add(OPAQUE_TAG);
		return item;
	}

	public static Object getOpaqueItem(MUIElement uiElement) {
		return uiElement.getTransientData().get(OPAQUE_ITEM_KEY);
	}

	public static boolean isOpaqueMenu(MUIElement item) {
		return item != null && item instanceof MMenu && item.getTags().contains(OPAQUE_TAG);
	}

	public static boolean isOpaqueMenuItem(MUIElement item) {
		return item != null && item instanceof MDirectMenuItem
				&& item.getTags().contains(OPAQUE_TAG);
	}

	public static boolean isOpaqueMenuSeparator(MUIElement item) {
		return item != null && item instanceof MMenuSeparator
				&& item.getTags().contains(OPAQUE_TAG);
	}

	public static boolean isOpaqueToolItem(MUIElement uiElement) {
		return uiElement != null && uiElement instanceof MDirectToolItem
				&& uiElement.getTags().contains(OPAQUE_TAG);
	}

	public static void setOpaqueItem(MUIElement uiElement, Object opaqueItem) {
		uiElement.getTransientData().put(OPAQUE_ITEM_KEY, opaqueItem);
	}

}
