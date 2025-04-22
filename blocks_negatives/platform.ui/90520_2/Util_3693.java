/*******************************************************************************
 * Copyright (c) 2011, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     RenÃ© Brandstetter - Bug 419749 - [Workbench] [e4 Workbench] - Remove the deprecated PackageAdmin
 *     Lars Vogel <Lars.Vogel@vogela.com> - Bug 449859
 ******************************************************************************/

package org.eclipse.e4.ui.internal.workbench;

import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.spi.RegistryContributor;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.service.log.LogService;

/**
 * Collection of URI-related utilities
 */
public class URIHelper {

	/**
	 * The schema identifier used for Eclipse platform references
	 */

	/**
	 * The schema identifier used for Eclipse bundlesclass reference
	 */

	/**
	 * The schema identifier used for EMF platform references
	 */

	/**
	 * The segment used to specify location in a plugin
	 */

	/**
	 * The segment used to specify location in a fragment
	 */

	static public String constructPlatformURI(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);
		if (bundleRevision == null)
			return null;

		StringBuffer tmp = new StringBuffer();
		tmp.append(PLATFORM_SCHEMA);
		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) == BundleRevision.TYPE_FRAGMENT)
			tmp.append(FRAGMENT_SEGMENT);
		else
			tmp.append(PLUGIN_SEGMENT);
		tmp.append(bundle.getSymbolicName());
		return tmp.toString();
	}

	static public String constructPlatformURI(IContributor contributor) {
		String bundleName;
		if (contributor instanceof RegistryContributor)
			bundleName = ((RegistryContributor) contributor).getActualName();
		else
			bundleName = contributor.getName();
		Bundle bundle = Activator.getDefault().getBundleForName(bundleName);
		return constructPlatformURI(bundle);
	}

	static public Bundle getBundle(String contributorURI) {
		URI uri;
		try {
			uri = new URI(contributorURI);
		} catch (URISyntaxException e) {
			Activator.log(LogService.LOG_ERROR, "Invalid contributor URI: " + contributorURI); //$NON-NLS-1$
			return null;
		}
		if (!PLATFORM_SCHEMA.equals(uri.getScheme()))
		return Activator.getDefault().getBundleForName(uri.getPath());
	}

	static public String EMFtoPlatform(org.eclipse.emf.common.util.URI uri) {
		if (!PLATFORM_SCHEMA_EMF.equals(uri.scheme()))
			return null;
		int segments = uri.segmentCount();
		if (segments > 2)
			uri = uri.trimSegments(segments - 2);
		return uri.toString();
	}

	/**
	 * Helper method which checks if given String represents a Platform URI.
	 *
	 * @param uri
	 *            a possible Platform URI
	 * @return true if the given string is not {@code null} and starts with
	 *         {@value #PLATFORM_SCHEMA}; false otherwise
	 */
	public static boolean isPlatformURI(String uri) {
		return uri != null && uri.startsWith(PLATFORM_SCHEMA);
	}

	/**
	 * Helper method which checks if given String represents a Bundleclass URI.
	 *
	 * @param uri
	 *            a possible Bundleclass URI
	 * @return true if the given string is not {@code null} and starts with
	 *         {@value #BUNDLECLASS_SCHEMA}; false otherwise
	 */
	public static boolean isBundleClassUri(String uri) {
		if (uri != null && uri.startsWith(BUNDLECLASS_SCHEMA)) {
			if (split.length == 2) {
				return true;
			}
		}
		return false;
	}

}
