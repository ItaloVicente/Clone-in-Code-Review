package org.eclipse.e4.ui.css.swt.resources;

import org.eclipse.e4.ui.css.swt.resources.SWTResourcesRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;
import static org.mockito.Mockito.*;

import junit.framework.TestCase;

@SuppressWarnings("restriction")
public class SWTResourcesRegistryTest extends TestCase {
	private Display display;
	
	@Override
	public void setUp() throws Exception {
		display = Display.getDefault();
	}
	
	public void testInvalidateResources() throws Exception {
		SWTResourcesRegistry registry = new SWTResourcesRegistry(null);
		registry.registerResource(Font.class, "font1", cachedResource(true));
		registry.registerResource(Font.class, "font2", cachedResource(true));
		registry.registerResource(Font.class, "font3", new Font(display, "Arial", 10, SWT.NORMAL));
		registry.registerResource(Color.class, "color1", new VolatileResource<Resource>(null));
		registry.registerResource(Color.class, "color2", new Color(display, 255, 0, 0));
		
		registry.invalidateResources(Color.class, Font.class);
		
		assertFalse(((VolatileResource<?>) registry.getResource(Font.class, "font1")).isValid());
		assertFalse(((VolatileResource<?>) registry.getResource(Font.class, "font2")).isValid());
		assertFalse(((VolatileResource<?>) registry.getResource(Color.class, "color1")).isValid());
		
		((Resource) registry.getResource(Font.class, "font3")).dispose();
		((Resource) registry.getResource(Color.class, "color2")).dispose();
	}
	
	public void testDisposeUnusedResources() throws Exception {
		Resource resource1 = mock(Resource.class);
		doReturn(false).when(resource1).isDisposed();
		
		Resource resource2 = mock(Resource.class);
		doReturn(true).when(resource2).isDisposed();
		
		SWTResourcesRegistry registry = new SWTResourcesRegistry(null);
		registry.addUnusedResource(resource1);
		registry.addUnusedResource(resource2);
		registry.addUnusedResource(null);
		
		
		registry.disposeUnusedResources();
		
		
		verify(resource1, atMost(1)).dispose();
		verify(resource2, never()).dispose();
	}
	
	private VolatileResource<Resource> cachedResource(final boolean valid) {
		return new VolatileResource<Resource>(null) {{
			setValid(valid);
		}};
	}
}
