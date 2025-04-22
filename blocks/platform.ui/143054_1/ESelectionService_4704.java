
package org.eclipse.e4.ui.workbench.modeling;

import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

public interface EPlaceholderResolver {
	public void resolvePlaceholderRef(MPlaceholder ph, MWindow refWin);
}
