package org.eclipse.ui.internal;

import java.net.URL;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.branding.IBundleGroupConstants;

public class BundleGroupProperties extends BrandingProperties implements
        IBundleGroupConstants {

    private final IBundleGroup bundleGroup;

    private ImageDescriptor featureImageDescriptor;

    private URL featureImageUrl;

    private String tipsAndTricksHref;

    private URL welcomePageUrl;

    private String welcomePerspective;

    private URL licenseUrl;

    private String featureLabel;

    private String featureId;

    private String providerName;

    private String versionId;

	private String brandingId;

	private String brandingVersion;

    public BundleGroupProperties(IBundleGroup bundleGroup) {
        if (bundleGroup == null) {
			throw new IllegalArgumentException();
		}
        this.bundleGroup = bundleGroup;
    }

    public ImageDescriptor getFeatureImage() {
        if (featureImageDescriptor == null) {
			featureImageDescriptor = getFeatureImage(bundleGroup);
		}
        return featureImageDescriptor;
    }

    public URL getFeatureImageUrl() {
        if (featureImageUrl == null) {
			featureImageUrl = getFeatureImageUrl(bundleGroup);
		}
        return featureImageUrl;
    }

    public String getTipsAndTricksHref() {
        if (tipsAndTricksHref == null) {
			tipsAndTricksHref = getTipsAndTricksHref(bundleGroup);
		}
        return tipsAndTricksHref;
    }

    public URL getWelcomePageUrl() {
        if (welcomePageUrl == null) {
			welcomePageUrl = getWelcomePageUrl(bundleGroup);
		}
        return welcomePageUrl;
    }

    public String getWelcomePerspective() {
        if (welcomePerspective == null) {
			welcomePerspective = getWelcomePerspective(bundleGroup);
		}
        return welcomePerspective;
    }

    public URL getLicenseUrl() {
        if (licenseUrl == null) {
			licenseUrl = getLicenseUrl(bundleGroup);
		}
        return licenseUrl;
    }

    public String getFeatureLabel() {
        if (featureLabel == null) {
			featureLabel = getFeatureLabel(bundleGroup);
		}
        return featureLabel;
    }

    public String getFeatureId() {
        if (featureId == null) {
			featureId = getFeatureId(bundleGroup);
		}
        return featureId;
    }

    public String getProviderName() {
        if (providerName == null) {
			providerName = getProviderName(bundleGroup);
		}
        return providerName;
    }

    public String getFeatureVersion() {
        if (versionId == null) {
			versionId = getFeatureVersion(bundleGroup);
		}
        return versionId;
    }
    
	public String getBrandingBundleId() {
		if (brandingId == null) {
			brandingId = getBrandingBundleId(bundleGroup);
		}
		return brandingId;
	}

	public String getBrandingBundleVersion() {
		if (brandingVersion == null) {
			brandingVersion = getBrandingBundleVersion(bundleGroup);
		}
		return brandingVersion;
	}

    public static ImageDescriptor getFeatureImage(IBundleGroup bundleGroup) {
        return getImage(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    public static URL getFeatureImageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(FEATURE_IMAGE), null);
    }

    public static String getTipsAndTricksHref(IBundleGroup bundleGroup) {
        return bundleGroup.getProperty(TIPS_AND_TRICKS_HREF);
    }

    public static URL getWelcomePageUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(WELCOME_PAGE), null);
    }

    public static String getWelcomePerspective(IBundleGroup bundleGroup) {
        String property = bundleGroup.getProperty(WELCOME_PERSPECTIVE);
        return property == null ? null : property;
    }

    public static URL getLicenseUrl(IBundleGroup bundleGroup) {
        return getUrl(bundleGroup.getProperty(LICENSE_HREF), null);
    }

    public static String getFeatureLabel(IBundleGroup bundleGroup) {
        return bundleGroup.getName();
    }

    public static String getFeatureId(IBundleGroup bundleGroup) {
        return bundleGroup.getIdentifier();
    }

    public static String getProviderName(IBundleGroup bundleGroup) {
        return bundleGroup.getProviderName();
    }

    public static String getFeatureVersion(IBundleGroup bundleGroup) {
        return bundleGroup.getVersion();
    }
    
	public static String getBrandingBundleId(IBundleGroup bundleGroup) {
		return bundleGroup.getProperty(BRANDING_BUNDLE_ID);
	}

	public static String getBrandingBundleVersion(IBundleGroup bundleGroup) {
		return bundleGroup.getProperty(BRANDING_BUNDLE_VERSION);
	}
}
