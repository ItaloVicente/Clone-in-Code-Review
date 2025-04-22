package org.eclipse.jface.resource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

public final class ResourceLocator {

	public static Optional<URL> locate(String symbolicName, String filePath) {
		IPath uriPath = new Path("/plugin").append(symbolicName).append(filePath); //$NON-NLS-1$
		URL url;
		try {
			URI uri = new URI("platform", null, uriPath.toString(), null); //$NON-NLS-1$
			url = uri.toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			return Optional.empty();
		}

		URL fullPathString = FileLocator.find(url);
		if (fullPathString == null) {
			try {
				url = new URL(filePath);
			} catch (MalformedURLException e) {
				return Optional.empty();
			}
		}
		return Optional.ofNullable(url);
	}

	public static Optional<ImageDescriptor> imageDescriptorFromBundle(String symbolicName, String imageFilePath) {
		Optional<URL> locate = locate(symbolicName, imageFilePath);
		if (locate.isPresent()) {
			return Optional.of(ImageDescriptor.createFromURL(locate.get()));
		}
		return Optional.empty();
	}

}
