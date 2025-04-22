
package org.eclipse.e4.ui.workbench.renderers.swt;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import junit.framework.TestCase;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.resources.SWTResourcesRegistry;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.renderers.swt.WBWRenderer.ThemeDefinitionChangedHandler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.osgi.service.event.Event;

public class ThemeDefinitionChangedHandlerTest extends TestCase {
	public void testHandleEventWhenThemeChanged() throws Exception {
		final MApplication application = ApplicationFactoryImpl.eINSTANCE
				.createApplication();
		application.getChildren().add(MBasicFactory.INSTANCE.createWindow());
		application.getChildren().add(MBasicFactory.INSTANCE.createWindow());

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(IEventBroker.DATA, application);

		Event event = new Event("topic", params);

		SWTResourcesRegistry registry = mock(SWTResourcesRegistry.class);

		CSSEngine engine = mock(CSSEngine.class);
		doReturn(registry).when(engine).getResourcesRegistry();

		ThemeDefinitionChangedHandlerTestable handler = spy(new ThemeDefinitionChangedHandlerTestable());
		doReturn(engine).when(handler).getEngine(any(MWindow.class));

		handler.handleEvent(event);

		verify(engine, times(2)).reapply();
		verify(registry, times(2)).invalidateResources(Font.class, Color.class);
	}

	public void testHandleEventWhenElementIsNotMApplication() throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(IEventBroker.DATA, MBasicFactory.INSTANCE.createWindow());

		Event event = new Event("topic", params);

		CSSEngine engine = mock(CSSEngine.class);

		ThemeDefinitionChangedHandlerTestable handler = spy(new ThemeDefinitionChangedHandlerTestable());
		doReturn(engine).when(handler).getEngine(any(MWindow.class));

		handler.handleEvent(event);

		verify(engine, never()).reapply();
	}

	public void testHandleEventWhenCSSEngineNotFoundForWidget()
			throws Exception {
		MWindow window1 = MBasicFactory.INSTANCE.createWindow();
		MWindow window2 = MBasicFactory.INSTANCE.createWindow();

		final MApplication application = ApplicationFactoryImpl.eINSTANCE
				.createApplication();
		application.getChildren().add(window1);
		application.getChildren().add(window2);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put(IEventBroker.DATA, application);

		Event event = new Event("topic", params);

		SWTResourcesRegistry registry = mock(SWTResourcesRegistry.class);

		CSSEngine engine = mock(CSSEngine.class);
		doReturn(registry).when(engine).getResourcesRegistry();

		ThemeDefinitionChangedHandlerTestable handler = spy(new ThemeDefinitionChangedHandlerTestable());
		doReturn(null).when(handler).getEngine(window1);
		doReturn(engine).when(handler).getEngine(window2);

		handler.handleEvent(event);

		verify(engine, times(1)).reapply();
		verify(registry, times(1)).invalidateResources(Font.class, Color.class);
	}

	protected static class ThemeDefinitionChangedHandlerTestable extends
			ThemeDefinitionChangedHandler {
		@Override
		public CSSEngine getEngine(MWindow window) {
			return super.getEngine(window);
		}
	}
}
