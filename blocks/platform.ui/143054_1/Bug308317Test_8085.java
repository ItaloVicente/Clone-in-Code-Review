
package org.eclipse.e4.ui.tests.workbench;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.custom.CTabFolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AreaRendererTest {
	protected IEclipseContext appContext;
	protected E4Workbench wb;
	private EModelService ems;

	@Before
	public void setUp() throws Exception {
		appContext = E4Application.createDefaultContext();
		appContext.set(IWorkbench.PRESENTATION_URI_ARG, PartRenderingEngine.engineURI);
		ems = appContext.get(EModelService.class);
	}

	@After
	public void tearDown() throws Exception {
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
	}

	@Test
	public void testMultipleStacksUnderTheAreaCreateACTabFolder() {
		MWindow window = ems.createModelElement(MWindow.class);
		MArea area = ems.createModelElement(MArea.class);
		MPartStack stack1 = ems.createModelElement(MPartStack.class);
		stack1.getChildren().add(ems.createModelElement(MPart.class));
		stack1.getChildren().add(ems.createModelElement(MPart.class));
		MPartStack stack2 = ems.createModelElement(MPartStack.class);
		stack2.getChildren().add(ems.createModelElement(MPart.class));
		stack2.getChildren().add(ems.createModelElement(MPart.class));
		area.getChildren().add(stack1);
		area.getChildren().add(stack2);
		window.getChildren().add(area);

		MApplication application = ems.createModelElement(MApplication.class);
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		Assert.assertTrue(area.getWidget() instanceof CTabFolder);
	}

	@Test
	public void testStackInsideMCompositePartDoesNotCreateACTabFolder() {
		MWindow window = ems.createModelElement(MWindow.class);
		MArea area = ems.createModelElement(MArea.class);
		MCompositePart composite = ems.createModelElement(MCompositePart.class);

		MPartStack stack1 = ems.createModelElement(MPartStack.class);
		stack1.getChildren().add(ems.createModelElement(MPart.class));
		stack1.getChildren().add(ems.createModelElement(MPart.class));
		MPartStack stack2 = ems.createModelElement(MPartStack.class);
		stack2.getChildren().add(ems.createModelElement(MPart.class));
		stack2.getChildren().add(ems.createModelElement(MPart.class));

		composite.getChildren().add(stack1);
		composite.getChildren().add(stack2);

		area.getChildren().add(composite);
		window.getChildren().add(area);

		MApplication application = ems.createModelElement(MApplication.class);
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class, application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		Assert.assertFalse(area.getWidget() instanceof CTabFolder);
	}
}
