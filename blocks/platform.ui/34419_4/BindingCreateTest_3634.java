package org.eclipse.e4.ui.bindings.tests;


import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class Activator implements BundleActivator {

	public static final String PLUGIN_ID = "org.eclipse.e4.ui.bindings.tests"; //$NON-NLS-1$

	private static Activator plugin;
	
	private IEclipseContext appContext;
	private IEclipseContext serviceContext;

	public static Activator getDefault() {
		return plugin;
	}

	public void start(BundleContext context) throws Exception {
		plugin = this;
		serviceContext = EclipseContextFactory.getServiceContext(context);
		appContext = serviceContext.createChild();
		addLogService(appContext);
	}

	private void addLogService(IEclipseContext context) {
		context.set(LogService.class.getName(), new LogService() {

			public void log(int level, String message) {
				System.out.println(level + ": " + message);
			}

			public void log(int level, String message, Throwable exception) {
				System.out.println(level + ": " + message);
				if (exception != null) {
					exception.printStackTrace();
				}
			}

			public void log(ServiceReference sr, int level, String message) {

			}

			public void log(ServiceReference sr, int level, String message,
					Throwable exception) {

			}
		});
	}

	public void stop(BundleContext context) throws Exception {
		serviceContext.dispose();
		plugin = null;
	}

	public IEclipseContext getGlobalContext() {
		return appContext;
	}

}
