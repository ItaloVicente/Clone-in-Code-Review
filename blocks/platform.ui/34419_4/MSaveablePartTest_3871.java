
package org.eclipse.e4.ui.tests.workbench;

import junit.framework.TestCase;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class MSashTest extends TestCase {
	protected IEclipseContext appContext;
	protected E4Workbench wb;

	@Override
	protected void setUp() throws Exception {
		appContext = E4Application.createDefaultContext();
		appContext.set(E4Workbench.PRESENTATION_URI_ARG,
				PartRenderingEngine.engineURI);
	}

	@Override
	protected void tearDown() throws Exception {
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
	}

	public void testSashWeights() {
		MWindow window = createSashWithNViews(2);

		MApplication application = ApplicationFactoryImpl.eINSTANCE
				.createApplication();
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class.getName(), application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		Widget topWidget = (Widget) window.getWidget();
		((Shell) topWidget).layout(true);
		MPartSashContainer sash = (MPartSashContainer) window.getChildren()
				.get(0);
		assertTrue("Should be an MPartSashContainer",
				sash instanceof MPartSashContainer);

		MPart part0 = (MPart) sash.getChildren().get(0);
		MPart part1 = (MPart) sash.getChildren().get(1);

		int cdVal0 = -1;
		try {
			cdVal0 = Integer.parseInt(part0.getContainerData());
		} catch (NumberFormatException e) {
		}
		assertTrue("Part0 data is not an integer", cdVal0 != -1);

		int cdVal1 = -1;
		try {
			cdVal1 = Integer.parseInt(part1.getContainerData());
		} catch (NumberFormatException e) {
		}
		assertTrue("Part1 data is not an integer", cdVal1 != -1);

		assertTrue("Values should be equal", cdVal0 == cdVal1);
	}

	private MWindow createSashWithNViews(int n) {
		final MWindow window = BasicFactoryImpl.eINSTANCE.createWindow();
		window.setHeight(300);
		window.setWidth(401);
		window.setLabel("MyWindow");
		MPartSashContainer sash = BasicFactoryImpl.eINSTANCE
				.createPartSashContainer();
		window.getChildren().add(sash);

		for (int i = 0; i < n; i++) {
			MPart contributedPart = BasicFactoryImpl.eINSTANCE.createPart();
			contributedPart.setLabel("Sample View" + i);
			contributedPart
					.setContributionURI("bundleclass://org.eclipse.e4.ui.tests/org.eclipse.e4.ui.tests.workbench.SampleView");
			sash.getChildren().add(contributedPart);
		}

		return window;
	}
}
