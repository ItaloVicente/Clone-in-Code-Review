
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.tweaklets.Tweaklets.TweakKey;

public abstract class GrabFocus {
	public static TweakKey KEY = new Tweaklets.TweakKey(GrabFocus.class);

	static {
		Tweaklets.setDefault(GrabFocus.KEY, new AllowGrabFocus());
	}

	public abstract boolean grabFocusAllowed(IWorkbenchPart part);

	public abstract void init(Display display);

	public abstract void dispose();
}
