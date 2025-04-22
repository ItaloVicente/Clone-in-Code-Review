
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.RectangleAnimationFeedbackBase;
import org.eclipse.ui.internal.RectangleAnimationImageFeedback;

public class ImageAnimationTweak extends Animations {
	public ImageAnimationTweak() {}
	
	@Override
	public RectangleAnimationFeedbackBase createFeedback(Shell shell) {
		return new RectangleAnimationImageFeedback(shell, null, null);
	}

}
