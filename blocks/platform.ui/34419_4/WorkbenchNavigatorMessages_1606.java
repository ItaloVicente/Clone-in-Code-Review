package org.eclipse.ui.internal.navigator.resources.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;

public class NavigatorUIPluginImages {

	private static URL fgIconLocation;

	private final static ImageRegistry NAVIGATORUIPLUGIN_REGISTRY = NavigatorPlugin.getDefault().getImageRegistry();

	static {
		String pathSuffix = "icons/full/"; //$NON-NLS-1$ 
		fgIconLocation = FileLocator.find(NavigatorPlugin.getDefault().getBundle(), new Path(pathSuffix), Collections.EMPTY_MAP);
	}

	public static Image get(String key) {
		return NAVIGATORUIPLUGIN_REGISTRY.get(key);
	}

	private static ImageDescriptor create(String prefix, String name) {
		return ImageDescriptor.createFromURL(makeIconFileURL(prefix, name));
	}

	private static URL makeIconFileURL(String prefix, String name) {
		StringBuffer buffer = new StringBuffer(prefix);
		buffer.append(name);
		try {
			return new URL(fgIconLocation, buffer.toString());
		} catch (MalformedURLException ex) {

			return null;
		}
	}

	public static void setLocalImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "lcl16/", iconName); //$NON-NLS-1$
	}

	public static void setImageDescriptors(IAction action, String type, String relPath) {
		action.setImageDescriptor(create("e" + type, relPath)); //$NON-NLS-1$
	}

}
