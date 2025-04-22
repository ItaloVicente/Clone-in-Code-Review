package org.eclipse.e4.ui.internal.about;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.WorkbenchMessages;
import org.eclipse.osgi.signedcontent.SignedContent;
import org.eclipse.osgi.signedcontent.SignedContentFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;



public class AboutBundleData extends AboutData {
    
	private Bundle bundle;
	
    private boolean isSignedDetermined = false;	

	private boolean isSigned;
    
    public AboutBundleData(Bundle bundle) {
        super(getResourceString(bundle, Constants.BUNDLE_VENDOR),
                getResourceString(bundle, Constants.BUNDLE_NAME),
                getResourceString(bundle, Constants.BUNDLE_VERSION), bundle
                        .getSymbolicName());
        
        this.bundle = bundle;
        
    }

    public int getState() {
        return bundle.getState();
    }

    public String getStateName() {
        switch (getState()) {
        case Bundle.INSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_installed;
        case Bundle.RESOLVED:
            return WorkbenchMessages.AboutPluginsDialog_state_resolved;
        case Bundle.STARTING:
            return WorkbenchMessages.AboutPluginsDialog_state_starting;
        case Bundle.STOPPING:
            return WorkbenchMessages.AboutPluginsDialog_state_stopping; 
        case Bundle.UNINSTALLED:
            return WorkbenchMessages.AboutPluginsDialog_state_uninstalled;
        case Bundle.ACTIVE:
            return WorkbenchMessages.AboutPluginsDialog_state_active; 
        default:
            return WorkbenchMessages.AboutPluginsDialog_state_unknown; 
        }
    }

    private static String getResourceString(Bundle bundle, String headerName) {
        String value = bundle.getHeaders().get(headerName);
        return value == null ? null : Platform.getResourceString(bundle, value);
    }

    public boolean isSignedDetermined() {
    	return isSignedDetermined;
    }

    public boolean isSigned() throws IllegalStateException {

		if (isSignedDetermined)
			return isSigned;

		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		
		ServiceReference<?> factoryRef = bundleContext
				.getServiceReference(SignedContentFactory.class.getName());
		if (factoryRef == null)
			throw new IllegalStateException();
		SignedContentFactory contentFactory = (SignedContentFactory) bundleContext
				.getService(factoryRef);
		try {
			SignedContent signedContent = contentFactory.getSignedContent(bundle);
			isSigned = signedContent != null && signedContent.isSigned();
			isSignedDetermined = true;
			return isSigned;
		} catch (IOException e) {
			throw (IllegalStateException) new IllegalStateException().initCause(e);
		} catch (GeneralSecurityException e){
			throw (IllegalStateException) new IllegalStateException().initCause(e);
		} finally {
			bundleContext.ungetService(factoryRef);
		}
	}

	public Bundle getBundle() {
		return bundle;
	}
    
}
