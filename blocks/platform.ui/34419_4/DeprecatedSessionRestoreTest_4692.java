package org.eclipse.ui.tests.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.WizardsRegistryReader;

public class Bug75118Test extends TestCase {

	public Bug75118Test() {
		super();
	}

	public Bug75118Test(String name) {
		super(name);
	}

	public void testWizards() {
		WizardsRegistryReader reader = new WizardsRegistryReader(
				PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_NEW);
		WorkbenchWizardElement [] primaryWizards = reader.getPrimaryWizards();
		Set wizardSet = new HashSet(Arrays.asList(primaryWizards));

		assertEquals(primaryWizards.length, wizardSet.size());
	}
}
