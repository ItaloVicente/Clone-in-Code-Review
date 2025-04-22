package org.eclipse.ui.tests.menus;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.ui.internal.menus.MenuHelper;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class MenuHelperTest extends TestCase {
	private IExtensionRegistry registry = RegistryFactory.getRegistry();

	public void test391232() {
		IExtension extension = registry
				.getExtension("org.eclipse.ui.tests.MenuHelperTest");
		assertNotNull(extension);
		assertTrue(extension.getConfigurationElements().length == 1);
		IConfigurationElement menuContribution = extension
				.getConfigurationElements()[0];
		assertEquals(IWorkbenchRegistryConstants.PL_MENU_CONTRIBUTION,
				menuContribution.getName());
		assertTrue(menuContribution.getChildren().length > 0);
		IConfigurationElement menuElement = menuContribution.getChildren()[0];
		assertEquals(IWorkbenchRegistryConstants.TYPE_MENU,
				menuElement.getName());
		assertNotNull(menuElement
				.getAttribute(IWorkbenchRegistryConstants.ATT_ICON));

		String uri = MenuHelper.getIconURI(menuElement,
				IWorkbenchRegistryConstants.ATT_ICON);
		assertNotNull(uri);
		assertEquals(
				"platform:/plugin/org.eclipse.ui/icons/full/obj16/fldr_obj.png",
				uri);
	}
}
