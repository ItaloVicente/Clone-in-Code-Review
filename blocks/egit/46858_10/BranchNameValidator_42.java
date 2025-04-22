package org.eclipse.egit.gitflow;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

	private static Plugin instance;

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		instance = this;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static IStatus error(String message, Throwable throwable) {
		return new Status(IStatus.ERROR, getPluginId(), 0, message, throwable);
	}

	public static IStatus error(Throwable throwable) {
		return error(throwable.getMessage(), throwable);
	}

	public static IStatus error(String message) {
		return new Status(IStatus.ERROR, getPluginId(), message);
	}

	public static Plugin getDefault() {
		return instance;
	}

	public static String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}
}
