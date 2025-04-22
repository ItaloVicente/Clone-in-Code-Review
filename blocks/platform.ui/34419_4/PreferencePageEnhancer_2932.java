
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.LegacyAnimationFeedback;
import org.eclipse.ui.internal.RectangleAnimationFeedbackBase;

public class LegacyAnimations extends Animations {
	public LegacyAnimations() {}
	
	@Override
	public RectangleAnimationFeedbackBase createFeedback(Shell shell) {
		return new LegacyAnimationFeedback(shell, null, null);
	}

}
