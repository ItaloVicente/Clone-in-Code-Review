
package org.eclipse.e4.ui.workbench.renderers.swt;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.resources.IResourcesRegistry;
import org.eclipse.e4.ui.css.swt.resources.ResourceByDefinitionKey;
import org.eclipse.e4.ui.css.swt.resources.SWTResourcesRegistry;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.impl.ApplicationFactoryImpl;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.renderers.swt.WBWRenderer.ThemeDefinitionChangedHandler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Resource;
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

		Resource resource1 = mock(Resource.class);
		doReturn(false).when(resource1).isDisposed();

		Resource resource2 = mock(Resource.class);
		doReturn(true).when(resource2).isDisposed();

		Object resource3 = new Object();

		List<Object> removedResources = new ArrayList<Object>();
		removedResources.add(resource1);
		removedResources.add(resource2);
		removedResources.add(resource3);

		SWTResourcesRegistry registry = mock(SWTResourcesRegistry.class);
		doReturn(removedResources).when(registry)
				.removeResourcesByKeyTypeAndType(ResourceByDefinitionKey.class,
						Font.class, Color.class);

		CSSEngine engine = mock(CSSEngine.class);
		doReturn(registry).when(engine).getResourcesRegistry();

		ThemeDefinitionChangedHandlerTestable handler = spy(new ThemeDefinitionChangedHandlerTestable());
		doReturn(engine).when(handler).getEngine(any(MWindow.class));

		handler.handleEvent(event);

		verify(engine, times(2)).reapply();

		verify(handler, times(1)).removeResources(registry);
		verify(handler, times(3)).disposeResource(any(Object.class));
		verify(handler, times(1)).disposeResource(resource1);
		verify(handler, times(1)).disposeResource(resource2);
		verify(handler, times(1)).disposeResource(resource3);

		verify(resource1, times(1)).isDisposed();
		verify(resource1, times(1)).dispose();

		verify(resource2, times(1)).isDisposed();
		verify(resource2, never()).dispose();
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
		verify(handler, never()).removeResources(any(IResourcesRegistry.class));
		verify(handler, never()).disposeResource(any(Object.class));
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
		verify(handler, times(1)).removeResources(registry);
		verify(handler, never()).disposeResource(any(Object.class));
	}

	protected static class ThemeDefinitionChangedHandlerTestable extends
			ThemeDefinitionChangedHandler {
		List<Object> processedRemovedResources;

		@Override
		public CSSEngine getEngine(MWindow window) {
			return super.getEngine(window);
		}

		@Override
		public List<Object> removeResources(IResourcesRegistry registry) {
			return super.removeResources(registry);
		}

		@Override
		public void disposeResource(Object resource) {
			super.disposeResource(resource);
		}
	}
}
