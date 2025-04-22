
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.RectangleAnimationFeedbackBase;
import org.eclipse.ui.internal.tweaklets.Tweaklets.TweakKey;

public abstract class Animations {
	public static TweakKey KEY = new Tweaklets.TweakKey(Animations.class);

	static {
		Tweaklets.setDefault(Animations.KEY, new LegacyAnimations());
	}

	public Animations() {
	}

	public abstract RectangleAnimationFeedbackBase createFeedback(Shell shell);
}
