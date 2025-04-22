package org.eclipse.ui.internal.navigator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class NavigatorImages {

	private final static ImageRegistry NAVIGATOR_PLUGIN_REGISTRY = NavigatorPlugin
			.getDefault().getImageRegistry();

	private static URL ICONS_LOCATION;
	static {
		ICONS_LOCATION = FileLocator.find(NavigatorPlugin.getDefault()
				.getBundle(), new Path("icons/full/"), Collections.EMPTY_MAP); //$NON-NLS-1$  
	}

	public static Image get(String key) {
		return NAVIGATOR_PLUGIN_REGISTRY.get(key);
	}

	public static ImageDescriptor createManaged(String prefix, String name) {
		ImageDescriptor result = ImageDescriptor.createFromURL(makeIconFileURL(
				prefix, name));
		NAVIGATOR_PLUGIN_REGISTRY.put(name, result);
		return result;
	}

	private static URL makeIconFileURL(String prefix, String name) {
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append(name);
		try {
			return new URL(ICONS_LOCATION, buffer.toString());
		} catch (MalformedURLException ex) {
			return null;
		}
	}
}
