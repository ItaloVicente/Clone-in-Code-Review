
package org.eclipse.e4.ui.tests.application;

import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

public class PostModelProcessor extends AbstractModelProcessorImpl {
	@Inject
	@Named("fragment.contributedWindow")
	private MWindow window;

	@Override
	protected void doRun() {
		if (window != null) {
			window.getVariables().add("postAddition");
		}
	}

	@Override
	protected String getSuffix() {
		return "post";
	}

}
