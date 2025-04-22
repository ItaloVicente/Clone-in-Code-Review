/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.about;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.signedcontent.SignedContent;
import org.eclipse.osgi.signedcontent.SignedContentFactory;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;



/**
 * A small class to manage the about dialog information for a single bundle.
 * @since 3.0
 */
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

    /**
    * @return a string representation of the argument state.
    *         Does not return null.
    */
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

    /**
     * A function to translate the resource tags that may be embedded in a
     * string associated with some bundle.
     *
     * @param headerName
     *            the used to retrieve the correct string
     * @return the string or null if the string cannot be found
     */
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

		BundleContext bundleContext = WorkbenchPlugin.getDefault()
				.getBundleContext();
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

	/**
	 * @return current bundle
	 */
	public Bundle getBundle() {
		return bundle;
	}

}
