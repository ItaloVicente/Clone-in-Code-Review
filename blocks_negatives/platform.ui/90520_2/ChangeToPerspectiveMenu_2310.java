/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;

/**
 * The branding properties are retrieved as strings, but often used as other
 * types (e.g., <code>java.net.URL</code>s. This class provides some utility
 * functions for converting the string values to these well known classes. This
 * may be subclassed by clients that use more than just these few types.
 */
public abstract class BrandingProperties {

    /**
     * Create an url from the argument absolute or relative path. The bundle
     * parameter is used as the base for relative paths and is allowed to be
     * null.
     *
     * @param value
     *            the absolute or relative path
     * @param definingBundle
     *            bundle to be used for relative paths (may be null)
     * @return
     */
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

    /**
     * Create a descriptor from the argument absolute or relative path to an
     * image file. bundle parameter is used as the base for relative paths and
     * is allowed to be null.
     *
     * @param value
     *            the absolute or relative path
     * @param definingBundle
     *            bundle to be used for relative paths (may be null)
     * @return
     */
    public static ImageDescriptor getImage(String value, Bundle definingBundle) {
        URL url = getUrl(value, definingBundle);
        return url == null ? null : ImageDescriptor.createFromURL(url);
    }

    /**
     * Returns a array of URL for the given property or <code>null</code>.
     * The property value should be a comma separated list of urls (either
     * absolute or relative to the argument bundle). Tokens that do not
     * represent a valid url will be represented with a null entry in the
     * returned array.
     *
     * @param value
     *            value of a property that contains a comma-separated list of
     *            product relative urls
     * @param definingBundle
     *            bundle to be used as base for relative paths (may be null)
     * @return a URL for the given property, or <code>null</code>
     */
    public static URL[] getURLs(String value, Bundle definingBundle) {
        if (value == null) {
			return null;
		}

        StringTokenizer tokens = new StringTokenizer(value, ",); //$NON-NLS-1$
-        ArrayList array = new ArrayList(10);
-        while (tokens.hasMoreTokens()) {
-			array.add(getUrl(tokens.nextToken().trim(), definingBundle));
-		}
-
-        return (URL[]) array.toArray(new URL[array.size()]);
-    }
-
-    /**
-     * Returns an array of image descriptors for the given property, or
-     * <code>null</code>. The property value should be a comma separated list
-     * of image paths. Each path should either be absolute or relative to the
-     * optional bundle parameter.
-     *
-     * @param value
-     *            value of a property that contains a comma-separated list of
-     *            product relative urls describing images
-     * @param definingBundle
-     *            bundle to be used for relative paths (may be null)
-     * @return an array of image descriptors for the given property, or
-     *         <code>null</code>
-     */
-    public static ImageDescriptor[] getImages(String value,
-            Bundle definingBundle) {
-        URL[] urls = getURLs(value, definingBundle);
-        if (urls == null || urls.length <= 0) {
-			return null;
-		}
-
-        ImageDescriptor[] images = new ImageDescriptor[urls.length];
-        for (int i = 0; i < images.length; ++i) {
-			images[i] = ImageDescriptor.createFromURL(urls[i]);
-		}
-
-        return images;
-    }
-}
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/BundleGroupProperties.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/BundleGroupProperties.java
deleted file mode 100644
index f4fdb0f57a..0000000000
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/BundleGroupProperties.java	
+++ /dev/null
@@ -1,284 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2004, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.internal;
-
-import java.net.URL;
-
-import org.eclipse.core.runtime.IBundleGroup;
-import org.eclipse.jface.resource.ImageDescriptor;
-import org.eclipse.ui.branding.IBundleGroupConstants;
-
-/**
- * A class that converts the strings returned by
- * <code>org.eclipse.core.runtime.IBundleGroup.getProperty</code> to the
- * appropriate class. This implementation is tightly bound to the properties
- * provided in IBundleGroupConstants. Clients adding their own properties could
- * choose to subclass this.
- *
- * @see org.eclipse.ui.branding.IBundleGroupConstants
- * @since 3.0
- */
-public class BundleGroupProperties extends BrandingProperties implements
-        IBundleGroupConstants {
-
-    private final IBundleGroup bundleGroup;
-
-    private ImageDescriptor featureImageDescriptor;
-
-    private URL featureImageUrl;
-
-    private String tipsAndTricksHref;
-
-    private URL welcomePageUrl;
-
-    private String welcomePerspective;
-
-    private URL licenseUrl;
-
-    private String featureLabel;
-
-    private String featureId;
-
-    private String providerName;
-
-    private String versionId;
-
-	private String brandingId;
-
-	private String brandingVersion;
-
-    /**
-     * This instance will return properties from the given bundle group.  The properties are
-     * retrieved in a lazy fashion and cached for later retrieval.
-     * @param bundleGroup must not be null
-     */
-    public BundleGroupProperties(IBundleGroup bundleGroup) {
-        if (bundleGroup == null) {
-			throw new IllegalArgumentException();
-		}
-        this.bundleGroup = bundleGroup;
-    }
-
-    /**
-     * An image which can be shown in an about features" dialog (32x32).
     */
    public ImageDescriptor getFeatureImage() {
        if (featureImageDescriptor == null) {
			featureImageDescriptor = getFeatureImage(bundleGroup);
		}
        return featureImageDescriptor;
    }

    /**
     * The URL to an image which can be shown in an "about features" dialog (32x32).
     */
    public URL getFeatureImageUrl() {
        if (featureImageUrl == null) {
			featureImageUrl = getFeatureImageUrl(bundleGroup);
		}
        return featureImageUrl;
    }

    /**
     * A help reference for the feature's tips and tricks page (optional).
     */
    public String getTipsAndTricksHref() {
        if (tipsAndTricksHref == null) {
			tipsAndTricksHref = getTipsAndTricksHref(bundleGroup);
		}
        return tipsAndTricksHref;
    }

    /**
     * A URL for the feature's welcome page (special XML-based format) ($nl$/
     * prefix to permit locale-specific translations of entire file). Products
     * designed to run "headless" typically would not have such a page.
     */
    public URL getWelcomePageUrl() {
        if (welcomePageUrl == null) {
			welcomePageUrl = getWelcomePageUrl(bundleGroup);
		}
        return welcomePageUrl;
    }

    /**
     * The id of a perspective in which to show the welcome page (optional).
     */
    public String getWelcomePerspective() {
        if (welcomePerspective == null) {
			welcomePerspective = getWelcomePerspective(bundleGroup);
		}
        return welcomePerspective;
    }

    /**
     * A URL for the feature's license page.
     */
    public URL getLicenseUrl() {
        if (licenseUrl == null) {
			licenseUrl = getLicenseUrl(bundleGroup);
		}
        return licenseUrl;
    }

    /**
     * Returns a label for the feature plugn, or <code>null</code>.
     */
    public String getFeatureLabel() {
        if (featureLabel == null) {
			featureLabel = getFeatureLabel(bundleGroup);
		}
        return featureLabel;
    }

    /**
     * Returns the id for this bundleGroup.
     */
    public String getFeatureId() {
        if (featureId == null) {
			featureId = getFeatureId(bundleGroup);
		}
        return featureId;
    }

    /**
     * Returns the provider name.
     */
    public String getProviderName() {
        if (providerName == null) {
			providerName = getProviderName(bundleGroup);
		}
        return providerName;
    }

    /**
     * Returns the feature version id.
     */
    public String getFeatureVersion() {
        if (versionId == null) {
			versionId = getFeatureVersion(bundleGroup);
		}
        return versionId;
    }

	/**
	 * @return the branding plugin id, or <code>null</code>
	 */
	public String getBrandingBundleId() {
		if (brandingId == null) {
			brandingId = getBrandingBundleId(bundleGroup);
		}
		return brandingId;
	}

	/**
	 * @return the branding plugin version, or <code>null</code>
	 */
	public String getBrandingBundleVersion() {
		if (brandingVersion == null) {
			brandingVersion = getBrandingBundleVersion(bundleGroup);
		}
		return brandingVersion;
	}

    /**
     * An image which can be shown in an "about features" dialog (32x32).
     */
    public static ImageDescriptor getFeatureImage(IBundleGroup bundleGroup) {
        return getImage(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    /**
     * The URL to an image which can be shown in an "about features" dialog (32x32).
     */
    public static URL getFeatureImageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    /**
     * A help reference for the feature's tips and tricks page (optional).
     */
    public static String getTipsAndTricksHref(IBundleGroup bundleGroup) {
        return bundleGroup.getProperty(TIPS_AND_TRICKS_HREF);
    }

    /**
     * A URL for the feature's welcome page (special XML-based format) ($nl$/
     * prefix to permit locale-specific translations of entire file). Products
     * designed to run "headless" typically would not have such a page.
     */
    public static URL getWelcomePageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(WELCOME_PAGE), null);
    }

    /**
     * The id of a perspective in which to show the welcome page (optional).
     */
    public static String getWelcomePerspective(IBundleGroup bundleGroup) {
        String property = bundleGroup.getProperty(WELCOME_PERSPECTIVE);
        return property == null ? null : property;
    }

    /**
     * A URL for the feature's license page.
     */
    public static URL getLicenseUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(LICENSE_HREF), null);
    }

    /**
     * Returns a label for the feature plugn, or <code>null</code>.
     */
    public static String getFeatureLabel(IBundleGroup bundleGroup) {
        return bundleGroup.getName();
    }

    /**
     * Returns the id for this bundleGroup.
     */
    public static String getFeatureId(IBundleGroup bundleGroup) {
        return bundleGroup.getIdentifier();
    }

    /**
     * Returns the provider name.
     */
    public static String getProviderName(IBundleGroup bundleGroup) {
        return bundleGroup.getProviderName();
    }

    /**
     * Returns the feature version id.
     */
    public static String getFeatureVersion(IBundleGroup bundleGroup) {
        return bundleGroup.getVersion();
    }

	/**
	 * A Feature's branding plugin id.
	 *
	 * @param bundleGroup
	 * @return the ID or <code>null</code> if not provided.
	 */
	public static String getBrandingBundleId(IBundleGroup bundleGroup) {
		return bundleGroup.getProperty(BRANDING_BUNDLE_ID);
	}

	/**
	 * A Feature's branding plugin version.
	 *
	 * @param bundleGroup
	 * @return the version, or <code>null</code> if not provided.
	 */
	public static String getBrandingBundleVersion(IBundleGroup bundleGroup) {
		return bundleGroup.getProperty(BRANDING_BUNDLE_VERSION);
	}
}
