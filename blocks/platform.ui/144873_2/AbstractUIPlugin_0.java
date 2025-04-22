package org.eclipse.jface.resource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class ResourceLocator {

	public static URL locate(Object container, String value) {
		try {
			if (value != null) {
				return new URL(value);
			}
		} catch (MalformedURLException e) {
			Bundle bundle = null;
			if (container instanceof Bundle) {
				bundle = (Bundle) container;
			} else if (container instanceof Class<?>) {
				bundle = FrameworkUtil.getBundle((Class<?>) container);
			}
			if (bundle != null) {
				return FileLocator.find(bundle, new Path(value));
			}
		}
		return null;
	}

	public static List<URL> locate(Object container, String value, String delimeter) {
		if (value == null) {
			return Collections.emptyList();
		}
		return Stream.of(value.split(delimeter)).map(v -> locate(container, v)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public static ImageDescriptor createImageDescriptor(Object container, String value) {
		return ImageDescriptor.createFromURL(locate(container, value));
	}

	public static List<ImageDescriptor> createImageDescriptors(Object container, String value, String delimeter) {
		return locate(container, value, delimeter).stream().map(u -> ImageDescriptor.createFromURL(u))
				.collect(Collectors.toList());
	}

}
