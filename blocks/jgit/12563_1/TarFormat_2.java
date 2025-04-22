package org.eclipse.jgit.pgm.archive;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

@SuppressWarnings("unused") public class FormatActivator
					implements BundleActivator {
	public void start(BundleContext context) throws Exception {
		ArchiveCommand.registerFormat("tar"
		ArchiveCommand.registerFormat("zip"
	}

	public void stop(BundleContext context) throws Exception {
		ArchiveCommand.unregisterFormat("zip");
		ArchiveCommand.unregisterFormat("tar");
	}
}
