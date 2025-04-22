
package org.eclipse.ui.examples.adapterservice.snippets;

import org.osgi.framework.BundleContext;

import org.osgi.framework.BundleActivator;

public class SnippetActivator implements BundleActivator {

	public static BundleContext bundleContext;
	
	public SnippetActivator() {
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		bundleContext = aContext;
		SnippetSetup.initializeServices();
	}
	@Override
	public void stop(BundleContext aContext) throws Exception {
		bundleContext = null;
	}
}
