package org.eclipse.e4.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

public abstract class BrandingProperties {

	public static URL getUrl(String value, Bundle definingBundle) {
		try {
			if (value != null) {
				return new URL(value);
			}
		} catch (MalformedURLException e) {
			if (definingBundle != null) {
				return Platform.find(definingBundle, new Path(value));
			}
		}

		return null;
	}

	public static ImageDescriptor getImage(String value, Bundle definingBundle) {
		URL url = getUrl(value, definingBundle);
		return url == null ? null : ImageDescriptor.createFromURL(url);
	}

	public static URL[] getURLs(String value, Bundle definingBundle) {
		if (value == null) {
			return null;
		}

		StringTokenizer tokens = new StringTokenizer(value, ","); //$NON-NLS-1$
		ArrayList array = new ArrayList(10);
		while (tokens.hasMoreTokens()) {
			array.add(getUrl(tokens.nextToken().trim(), definingBundle));
		}

		return (URL[]) array.toArray(new URL[array.size()]);
	}

	public static ImageDescriptor[] getImages(String value, Bundle definingBundle) {
		URL[] urls = getURLs(value, definingBundle);
		if (urls == null || urls.length <= 0) {
			return null;
		}

		ImageDescriptor[] images = new ImageDescriptor[urls.length];
		for (int i = 0; i < images.length; ++i) {
			images[i] = ImageDescriptor.createFromURL(urls[i]);
		}

		return images;
	}
}
