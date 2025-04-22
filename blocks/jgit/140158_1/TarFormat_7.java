package org.eclipse.jgit.archive;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class FormatActivator implements BundleActivator {
	@Override
	public void start(BundleContext context) {
		ArchiveFormats.registerAll();
	}

	@Override
	public void stop(BundleContext context) {
		ArchiveFormats.unregisterAll();
	}
}
