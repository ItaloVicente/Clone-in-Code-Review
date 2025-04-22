package org.eclipse.ui.examples.fieldassist;

import org.eclipse.ui.plugin.*;
import org.osgi.framework.BundleContext;

public class FieldAssistPlugin extends AbstractUIPlugin {

	private static FieldAssistPlugin plugin;
	
	static String DEC_CONTENTASSIST = "org.eclipse.ui.examples.fieldassist.contentAssistDecoration";
	
	public FieldAssistPlugin() {
		plugin = this;
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	public static FieldAssistPlugin getDefault() {
		return plugin;
	}
}

