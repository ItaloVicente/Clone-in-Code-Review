
package org.eclipse.ui.tests.internal;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class StickyViewManagerTest extends UITestCase {

	private boolean originalPreference;

	public StickyViewManagerTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		originalPreference = PlatformUI.getPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR);
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR,
				false);
		super.doSetUp();
	}

	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR,
				originalPreference);
	}

	public void testMultipleStickyViewAcrossPerspectivesBug280656()
			throws Exception {
		IWorkbenchPage page = fWorkbench.getActiveWorkbenchWindow()
				.getActivePage();
		page.showView("org.eclipse.ui.tests.api.MockViewPartMultSticky", null,
				IWorkbenchPage.VIEW_ACTIVATE);
		page.showView("org.eclipse.ui.tests.api.MockViewPartMultSticky",
				"secondary", IWorkbenchPage.VIEW_ACTIVATE);

		IPerspectiveRegistry registry = fWorkbench.getPerspectiveRegistry();
		IPerspectiveDescriptor[] descriptors = registry.getPerspectives();

		for (int i = 0; i < descriptors.length; i++) {
			page.setPerspective(descriptors[i]);
			assertNotNull(page.findViewReference(
					"org.eclipse.ui.tests.api.MockViewPartMultSticky", null));
			assertNotNull(page.findViewReference(
					"org.eclipse.ui.tests.api.MockViewPartMultSticky",
					"secondary"));
		}
	}

	public void testRemovedMultipleStickyViewAcrossPerspectives()
			throws Exception {
		testMultipleStickyViewAcrossPerspectivesBug280656();

		IWorkbenchPage page = fWorkbench.getActiveWorkbenchWindow()
				.getActivePage();
		IViewReference primaryViewReference = page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", null);
		IViewReference secondaryViewReference = page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", "secondary");

		page.hideView(primaryViewReference);
		page.hideView(secondaryViewReference);

		IPerspectiveRegistry registry = fWorkbench.getPerspectiveRegistry();
		IPerspectiveDescriptor[] descriptors = registry.getPerspectives();

		for (int i = 0; i < descriptors.length; i++) {
			page.setPerspective(descriptors[i]);
			assertNull(page.findViewReference(
					"org.eclipse.ui.tests.api.MockViewPartMultSticky", null));
			assertNull(page.findViewReference(
					"org.eclipse.ui.tests.api.MockViewPartMultSticky",
					"secondary"));
		}
	}

	public void testRemovedMultipleStickyViewAcrossPerspectives2()
			throws Exception {
		IPerspectiveRegistry registry = fWorkbench.getPerspectiveRegistry();
		IPerspectiveDescriptor resourcePerspectiveDescriptor = registry
				.findPerspectiveWithId("org.eclipse.ui.resourcePerspective");
		IPerspectiveDescriptor viewPerspectiveDescriptor = registry
				.findPerspectiveWithId("org.eclipse.ui.tests.api.ViewPerspective");

		IWorkbenchPage page = fWorkbench.getActiveWorkbenchWindow()
				.getActivePage();
		page.setPerspective(resourcePerspectiveDescriptor);

		IViewPart primaryViewPart = page.showView(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", null,
				IWorkbenchPage.VIEW_ACTIVATE);
		page.showView("org.eclipse.ui.tests.api.MockViewPartMultSticky",
				"secondary", IWorkbenchPage.VIEW_ACTIVATE);
		IViewPart outlineViewPart = page.showView(IPageLayout.ID_OUTLINE, null,
				IWorkbenchPage.VIEW_ACTIVATE);

		page.setPerspective(viewPerspectiveDescriptor);

		assertNotNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", null));
		assertNotNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", "secondary"));

		page.showView(IPageLayout.ID_OUTLINE, null,
				IWorkbenchPage.VIEW_ACTIVATE);

		page.setPerspective(resourcePerspectiveDescriptor);

		assertNotNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", null));
		assertNotNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", "secondary"));
		assertNotNull(page.findViewReference(IPageLayout.ID_OUTLINE, null));

		page.hideView(primaryViewPart);
		page.hideView(outlineViewPart);

		page.setPerspective(viewPerspectiveDescriptor);

		assertNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", null));
		assertNotNull(page.findViewReference(
				"org.eclipse.ui.tests.api.MockViewPartMultSticky", "secondary"));
		assertNotNull(page.findViewReference(IPageLayout.ID_OUTLINE, null));
	}
}
