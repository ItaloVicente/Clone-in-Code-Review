
package org.eclipse.e4.ui.tests.application;

import java.util.List;
import junit.framework.TestCase;
import org.eclipse.e4.ui.internal.workbench.E4XMIResource;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class ModelRobustnessTest extends TestCase {

	public void testLoadingInvalidContainments() {
		URI uri = URI.createPlatformPluginURI(
				"org.eclipse.e4.ui.tests/xmi/InvalidContainment.e4xmi", true);
		ResourceSet set = new ResourceSetImpl();
		Resource resource = null;

		try {
			resource = set.getResource(uri, true);
			fail("This should have thrown an exception");
		} catch (Exception e) {
			resource = set.getResource(uri, false);
		}

		assertNotNull(resource);
		assertEquals(E4XMIResource.class, resource.getClass());
		assertEquals(1, resource.getContents().size());
		MApplication app = (MApplication) resource.getContents().get(0);
		assertEquals(1, app.getChildren().size());
		MWindow w = app.getChildren().get(0);
		assertEquals("window1", w.getElementId());
		assertEquals(2, w.getChildren().size());
		MPartStack stack = (MPartStack) w.getChildren().get(0);
		assertEquals("window1.partstack1", stack.getElementId());
		assertEquals(2, stack.getChildren().size());
		assertEquals("window1.partstack1.part1", stack.getChildren().get(0)
				.getElementId());
		assertEquals("window1.partstack1.inputpart1", stack.getChildren()
				.get(1).getElementId());

		stack = (MPartStack) w.getChildren().get(1);
		assertEquals("window1.partstack2", stack.getElementId());
		assertEquals(1, stack.getChildren().size());
		assertEquals("window1.partstack2.part1", stack.getChildren().get(0)
				.getElementId());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testAddingInvalidElements() {
		MApplication app = MApplicationFactory.INSTANCE.createApplication();
		List l = app.getChildren();
		l.add(MBasicFactory.INSTANCE.createWindow());
		try {
			l.add(MBasicFactory.INSTANCE.createPart());
			fail("The adding of this should have failed");
		} catch (IllegalArgumentException e) {
		} catch (ArrayStoreException e) {
		} catch (ClassCastException e) {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		l.add(MBasicFactory.INSTANCE.createWindow());
		assertEquals(2, l.size());
	}
}
