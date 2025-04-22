/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
/**
 * Utility class to handle image resources.
 */
public class ImageResource {
	private static ImageRegistry imageRegistry;

	private static Map<String, ImageDescriptor> imageDescriptors;

	private static Image[] busyImages;






	/**
	 * Cannot construct an ImageResource. Use static methods only.
	 */
	private ImageResource() {
	}

	/**
	 * Returns the busy images for the Web browser.
	 *
	 * @return org.eclipse.swt.graphics.Image[]
	 */
	public static Image[] getBusyImages() {
		return busyImages;
	}

	/**
	 * Return the image with the given key.
	 *
	 * @param key java.lang.String
	 * @return org.eclipse.swt.graphics.Image
	 */
	public static Image getImage(String key) {
		if (imageRegistry == null)
			initializeImageRegistry();
		return imageRegistry.get(key);
	}

	/**
	 * Return the image descriptor with the given key.
	 *
	 * @param key java.lang.String
	 * @return org.eclipse.jface.resource.ImageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		if (imageRegistry == null)
			initializeImageRegistry();
		return imageDescriptors.get(key);
	}

	/**
	 * Initialize the image resources.
	 */
	protected static void initializeImageRegistry() {
		imageRegistry = new ImageRegistry();
		imageDescriptors = new HashMap<>();

		registerImage(IMG_ELCL_NAV_BACKWARD, URL_ELCL + "nav_backward.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_FORWARD, URL_ELCL + "nav_forward.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_STOP, URL_ELCL + "nav_stop.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_REFRESH, URL_ELCL + "nav_refresh.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_GO, URL_ELCL + "nav_go.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_HOME, URL_ELCL + "nav_home.png"); //$NON-NLS-1$
		registerImage(IMG_ELCL_NAV_PRINT, URL_ELCL + "nav_print.png"); //$NON-NLS-1$

		registerImage(IMG_CLCL_NAV_BACKWARD, URL_CLCL + "nav_backward.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_FORWARD, URL_CLCL + "nav_forward.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_STOP, URL_CLCL + "nav_stop.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_REFRESH, URL_CLCL + "nav_refresh.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_GO, URL_CLCL + "nav_go.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_HOME, URL_CLCL + "nav_home.png"); //$NON-NLS-1$
		registerImage(IMG_CLCL_NAV_PRINT, URL_CLCL + "nav_print.png"); //$NON-NLS-1$

		registerImage(IMG_DLCL_NAV_BACKWARD, URL_DLCL + "nav_backward.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_FORWARD, URL_DLCL + "nav_forward.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_STOP, URL_DLCL + "nav_stop.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_REFRESH, URL_DLCL + "nav_refresh.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_GO, URL_DLCL + "nav_go.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_HOME, URL_DLCL + "nav_home.png"); //$NON-NLS-1$
		registerImage(IMG_DLCL_NAV_PRINT, URL_DLCL + "nav_print.png"); //$NON-NLS-1$

		registerImage(IMG_INTERNAL_BROWSER, URL_OBJ + "internal_browser.png"); //$NON-NLS-1$
		registerImage(IMG_EXTERNAL_BROWSER, URL_OBJ + "external_browser.png"); //$NON-NLS-1$

		busyImages = new Image[13];
		for (int i = 0; i < 13; i++) {
			registerImage("busy" + i, URL_OBJ + "busy/" + (i+1) + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	/**
	 * Register an image with the registry.
	 *
	 * @param key java.lang.String
	 * @param partialURL java.lang.String
	 */
	private static void registerImage(String key, String partialURL) {
		try {
			URL url = FileLocator.find(WebBrowserUIPlugin.getInstance().getBundle(), new Path(partialURL), null);
			ImageDescriptor id = ImageDescriptor.createFromURL(url);
			imageRegistry.put(key, id);
			imageDescriptors.put(key, id);
		} catch (Exception e) {
			Trace.trace(Trace.WARNING, "Error registering image " + key + " from " + partialURL, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
