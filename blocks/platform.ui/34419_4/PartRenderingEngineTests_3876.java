
package org.eclipse.e4.ui.tests.workbench;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import junit.framework.TestCase;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.MUILabel;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.menu.MToolControl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuFactoryImpl;
import org.eclipse.e4.ui.tests.Activator;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class PartFocusTest extends TestCase {

	protected IEclipseContext appContext;
	protected E4Workbench wb;

	protected EPartService eps;
	protected MWindow window;

	protected MPart part;
	protected MToolControl toolControl;

	protected MPart otherPart;

	protected void setUp() throws Exception {
		appContext = E4Application.createDefaultContext();
		appContext.set(E4Workbench.PRESENTATION_URI_ARG,
				PartRenderingEngine.engineURI);

		window = BasicFactoryImpl.eINSTANCE.createWindow();
		window.setWidth(500);
		window.setHeight(500);

		MPartSashContainer sash = BasicFactoryImpl.eINSTANCE
				.createPartSashContainer();
		window.getChildren().add(sash);
		window.setSelectedElement(sash);

		MPartStack stack = BasicFactoryImpl.eINSTANCE.createPartStack();
		sash.getChildren().add(stack);
		sash.setSelectedElement(stack);

		part = BasicFactoryImpl.eINSTANCE.createPart();
		part.setElementId("Part");
		part.setLabel("Part");
		part.setToolbar(MenuFactoryImpl.eINSTANCE.createToolBar());
		part.setContributionURI(Activator.asURI(PartBackend.class));
		stack.getChildren().add(part);

		toolControl = MenuFactoryImpl.eINSTANCE.createToolControl();
		toolControl.setElementId("ToolControl");
		toolControl.setContributionURI(Activator.asURI(TextField.class));
		part.getToolbar().getChildren().add(toolControl);

		stack = BasicFactoryImpl.eINSTANCE.createPartStack();
		sash.getChildren().add(stack);
		sash.setSelectedElement(stack);

		otherPart = BasicFactoryImpl.eINSTANCE.createPart();
		otherPart.setElementId("OtherPart");
		otherPart.setLabel("OtherPart");
		otherPart.setContributionURI(Activator.asURI(PartBackend.class));
		stack.getChildren().add(otherPart);

		MApplication application = ApplicationFactoryImpl.eINSTANCE
				.createApplication();
		application.getChildren().add(window);
		application.setContext(appContext);
		appContext.set(MApplication.class.getName(), application);

		wb = new E4Workbench(application, appContext);
		wb.createAndRunUI(window);

		eps = window.getContext().get(EPartService.class);
		eps.activate(part);
		eps.activate(otherPart);
		processEvents();

		assertNotNull(part.getObject());
		assertNotNull(toolControl.getObject());
		assertNotNull(otherPart.getObject());

		assertNotNull(part.getWidget());
		assertNotNull(toolControl.getWidget());
		assertNotNull(otherPart.getWidget());

		eps.activate(otherPart);
		processEvents();
		assertTrue(((PartBackend) otherPart.getObject()).text1.isFocusControl());
	}

	@Override
	protected void tearDown() throws Exception {
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
	}

	private void processEvents() {
		Display display = Display.getCurrent();
		if (display.isDisposed()) {
			return;
		}
		try {
			while (display.readAndDispatch()) {
				appContext.processWaiting();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFocusChangesOnExplicitPartActivation() {
		assertFalse(((PartBackend) part.getObject()).text1.isFocusControl());
		eps.activate(part);
		processEvents();
		assertTrue(((PartBackend) part.getObject()).text1.isFocusControl());
	}

	public void XXXtestNoFocusChangeOnExplicitWidgetSelection() {
		assertFalse(((PartBackend) part.getObject()).text1.isFocusControl());
		((TextField) toolControl.getObject()).text.setFocus();
		processEvents();
		assertEquals(part.getElementId(), eps.getActivePart().getElementId());
		assertFalse(((PartBackend) part.getObject()).text1.isFocusControl());
		assertTrue(((TextField) toolControl.getObject()).text.isFocusControl());
	}

	public void testNoActivationOnExplicitInPartWidgetSelection() {
		assertTrue(eps.getActivePart() == otherPart);
		assertTrue(((PartBackend) otherPart.getObject()).text1.isFocusControl());

		final boolean[] changed = new boolean[] { false };
		eps.addPartListener(new IPartListener() {
			public void partVisible(MPart part) {
				changed[0] = true;
			}

			public void partHidden(MPart part) {
				changed[0] = true;
			}

			public void partDeactivated(MPart part) {
				changed[0] = true;
			}

			public void partBroughtToTop(MPart part) {
				changed[0] = true;
			}

			public void partActivated(MPart part) {
				changed[0] = true;
			}
		});

		((PartBackend) otherPart.getObject()).text2.setFocus();
		processEvents();
		assertTrue(((PartBackend) otherPart.getObject()).text2.isFocusControl());
		assertFalse(changed[0]);

	}

}

class PartBackend {
	@Inject
	public Composite parent;
	@Inject
	MPart part;

	public Text text1;
	public Text text2;

	public PartBackend() {
	}

	@PostConstruct
	public void init() {
		text1 = new Text(parent, SWT.SINGLE);
		text2 = new Text(parent, SWT.SINGLE);
		text1.setText(part.getLabel() + " text1");
		text1.setText(part.getLabel() + " text2");
	}

	@Focus
	public void setFocus() {
		text1.setFocus();
	}

	@PreDestroy
	public void dispose() {
	}

}

class TextField {
	@Inject
	Composite parent;
	@Inject
	MUILabel element;

	public Text text;

	public TextField() {
	}

	@PostConstruct
	public void init() {
		text = new Text(parent, SWT.SINGLE);
		text.setText(element.getLabel() + " text");
	}

	@PreDestroy
	public void dispose() {
	}
}
