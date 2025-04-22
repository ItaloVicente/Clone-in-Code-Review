
package org.eclipse.e4.ui.progress;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.progress.internal.ProgressManagerUtil;

public class OpenProgressViewHandler {
	
	@Execute
	public void openProgressView() {
		ProgressManagerUtil.openProgressView();
	}
}
