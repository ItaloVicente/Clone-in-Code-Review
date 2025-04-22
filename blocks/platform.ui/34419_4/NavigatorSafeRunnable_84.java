package org.eclipse.ui.internal.navigator;

import java.util.Collections;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.services.IEvaluationService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

public class NavigatorPlugin extends AbstractUIPlugin {
	private static NavigatorPlugin plugin;
	
	private static final int LOG_DELAY = 100;
	
	public static final int ACTION_BAR_DELAY = 100;

	public static final int LINK_HELPER_DELAY = ACTION_BAR_DELAY + 20;

	private static class LogJob extends Job { 		
		
		
		private ListenerList messages = new ListenerList() {
			
			@Override
			public synchronized Object[] getListeners() {
				Object[] mesgs = super.getListeners();
				clear();
				return mesgs;
			}
		};

		
		public LogJob() {
			super("");  //$NON-NLS-1$
			setSystem(true); 
		}
		
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			
			Object[] mesgs = messages.getListeners();
			ILog pluginLog = getDefault().getLog();
			for (int i = 0; i < mesgs.length; i++) {
				pluginLog.log((IStatus)mesgs[i]);
			}
			return Status.OK_STATUS;
						
		}
		
		public void log(IStatus mesg) {
			messages.add(mesg);

		}
		
	}
	
	private static final LogJob logJob = new LogJob(); 

	public static String PLUGIN_ID = "org.eclipse.ui.navigator"; //$NON-NLS-1$

	private BundleListener bundleListener = new BundleListener() {
		@Override
		public void bundleChanged(BundleEvent event) {
			NavigatorSaveablesService.bundleChanged(event);
		}
	};

	public NavigatorPlugin() {
		super();
		plugin = this;
	}

	public static NavigatorPlugin getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	} 
	

	public Image getImage(String path) {
		Image image = getImageRegistry().get(path);
		if(image == null) {
			ImageDescriptor descriptor = getImageDescriptor(path);
			if(descriptor != null) {
				getImageRegistry().put(path, image = descriptor.createImage());
			}
		}
		return image;
	} 

	public static void logError(int aCode, String aMessage,
			Throwable anException) {
		getDefault().getLog().log(
				createErrorStatus(aCode, aMessage, anException));
	}

	public static void log(int severity, int aCode, String aMessage,
			Throwable exception) {
		log(createStatus(severity, aCode, aMessage, exception));
	}

	public static void log(IStatus aStatus) {
		logJob.log(aStatus);
		logJob.schedule(LOG_DELAY);
	}

	public static IEvaluationContext getApplicationContext() {
		IEvaluationService es = (IEvaluationService) PlatformUI.getWorkbench().getService(
				IEvaluationService.class);
		return es == null ? null : es.getCurrentState();
	}

	public static IEvaluationContext getEmptyEvalContext() {
		IEvaluationContext c = new EvaluationContext(getApplicationContext(),
				Collections.EMPTY_LIST);
		c.setAllowPluginActivation(true);
		return c;
	}

	public static IEvaluationContext getEvalContext(Object selection) {
		IEvaluationContext c = new EvaluationContext(getApplicationContext(), selection);
		c.setAllowPluginActivation(true);
		return c;
	}
	
	
	public static class Evaluator implements ISafeRunnable {
		EvaluationResult result;
		Expression expression;
		IEvaluationContext scope;

		@Override
		public void handleException(Throwable exception) {
			result = EvaluationResult.FALSE;
		}

		@Override
		public void run() throws Exception {
			result = expression.evaluate(scope);
		}
	}

	public static EvaluationResult safeEvaluate(Expression expression, IEvaluationContext scope) {
		Evaluator evaluator = new Evaluator();
		evaluator.expression = expression;
		evaluator.scope = scope;
		SafeRunner.run(evaluator);
		return evaluator.result;
	}
	
	public static IStatus createStatus(int severity, int aCode,
			String aMessage, Throwable exception) {
		return new Status(severity, PLUGIN_ID, aCode,
				aMessage != null ? aMessage : "No message.", exception); //$NON-NLS-1$
	}

	public static IStatus createErrorStatus(int aCode, String aMessage,
			Throwable exception) {
		return createStatus(IStatus.ERROR, aCode, aMessage, exception);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		context.addBundleListener(bundleListener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		context.removeBundleListener(bundleListener);
		super.stop(context);
	}
	
}
