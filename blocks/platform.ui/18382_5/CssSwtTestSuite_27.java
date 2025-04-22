package org.eclipse.e4.ui.css.swt.resources;

import junit.framework.TestCase;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Display;

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
	
	private VolatileResource<Resource> cachedResource(final boolean valid) {
		return new VolatileResource<Resource>(null) {{
			setValid(valid);
		}};
	}
}
