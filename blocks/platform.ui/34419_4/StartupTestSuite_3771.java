
package org.eclipse.e4.ui.tests.application;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.E4XMIResource;
import org.eclipse.e4.ui.internal.workbench.ResourceHandler;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

public class ResourceHandlerTest extends HeadlessStartupTest {
	private ServiceTracker locationTracker;

	public Location getInstanceLocation() {
		if (locationTracker == null) {
			BundleContext context = FrameworkUtil.getBundle(
					ResourceHandlerTest.class).getBundleContext();
			Filter filter = null;
			try {
				filter = context.createFilter(Location.INSTANCE_FILTER);
			} catch (InvalidSyntaxException e) {
			}
			locationTracker = new ServiceTracker(context, filter, null);
			locationTracker.open();
		}
		return (Location) locationTracker.getService();
	}

	private ResourceHandler createHandler(URI uri) {
		IEclipseContext localContext = applicationContext.createChild();
		localContext.set(E4Workbench.INSTANCE_LOCATION, getInstanceLocation());
		localContext.set(E4Workbench.PERSIST_STATE, Boolean.TRUE);
		localContext.set(E4Workbench.CLEAR_PERSISTED_STATE, Boolean.TRUE);
		localContext.set(E4Workbench.DELTA_RESTORE, Boolean.TRUE);

		localContext.set(E4Workbench.INITIAL_WORKBENCH_MODEL_URI, uri);

		return ContextInjectionFactory
				.make(ResourceHandler.class, localContext);

	}



	public void testModelProcessor() {
		URI uri = URI.createPlatformPluginURI(
				"org.eclipse.e4.ui.tests/xmi/modelprocessor/base.e4xmi", true);

		ResourceHandler handler = createHandler(uri);
		Resource resource = handler.loadMostRecentModel();
		MApplication application = (MApplication) resource.getContents().get(0);
		assertNotNull(application);
		assertEquals(2, application.getChildren().size());
		assertEquals("fragment.contributedWindow", application.getChildren()
				.get(1).getElementId());

		assertEquals("_w4fQ8HVHEd-aXt9fFntEtw",
				((E4XMIResource) resource).getID((EObject) application
						.getChildren().get(1))); // Window Id
		assertEquals(
				"_rdlLgJQUEd-6X_lmWgGEDA",
				((E4XMIResource) resource).getID((EObject) application
						.getChildren().get(1).getChildren().get(0))); // Perspective
		assertEquals("platform:/plugin/org.eclipse.e4.ui.tests", application
				.getChildren().get(1).getContributorURI()); // Window
		assertEquals("platform:/plugin/org.eclipse.e4.ui.tests", application
				.getChildren().get(1).getChildren().get(0).getContributorURI()); // Perspective

		if (application.getHandlers().size() > 2) {
			String check = "bundleclass://org.eclipse.e4.tools.emf.liveeditor/org.eclipse.e4.tools.emf.liveeditor.OpenLiveDialogHandler";
			if (check.equals(application.getHandlers().get(0)
					.getContributionURI())) {
				application.getHandlers().remove(0);
			} else if (check.equals(application.getHandlers().get(1)
					.getContributionURI())) {
				application.getHandlers().remove(1);
			}
		}

		assertTrue(application.getHandlers().size() > 0);
		assertSame(application.getCommands().get(0), application.getHandlers()
				.get(0).getCommand());
		assertEquals(2, application.getCommands().get(0).getParameters().size());
		assertEquals(1, application.getChildren().get(1).getVariables().size());
		assertNotNull(application.getChildren().get(0).getMainMenu());
		assertEquals(8, application.getChildren().get(0).getChildren().size());
		assertEquals("fragment.contributedPosFirst", application.getChildren()
				.get(0).getChildren().get(0).getElementId());
		assertEquals("fragment.contributedPos1",
				application.getChildren().get(0).getChildren().get(1)
						.getElementId());
		assertEquals("fragment.contributedBeforePart1", application
				.getChildren().get(0).getChildren().get(2).getElementId());
		assertEquals("fragment.contributedAfterPart1", application
				.getChildren().get(0).getChildren().get(4).getElementId());
		assertEquals("fragment.contributedBeforePart2", application
				.getChildren().get(0).getChildren().get(5).getElementId());
		assertEquals("fragment.contributedAfterPart2", application
				.getChildren().get(0).getChildren().get(7).getElementId());
	}

}
