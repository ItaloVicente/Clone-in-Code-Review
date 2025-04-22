
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.tweaklets.Tweaklets.TweakKey;

public abstract class PreferencePageEnhancer {
	public static TweakKey KEY = new Tweaklets.TweakKey(PreferencePageEnhancer.class);

	static {
		Tweaklets.setDefault(PreferencePageEnhancer.KEY, new DummyPrefPageEnhancer());
	}

	public abstract void createContents(Composite parent);

	public abstract void setSelection(Object selection);

	public abstract void performOK();

	public abstract void performCancel();

	public abstract void performDefaults();

}
