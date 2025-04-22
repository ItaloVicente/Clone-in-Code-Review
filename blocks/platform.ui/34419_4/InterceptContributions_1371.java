
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.tweaklets.Tweaklets.TweakKey;

public abstract class InterceptContributions {
	public static TweakKey KEY = new Tweaklets.TweakKey(
			InterceptContributions.class);

	static {
		Tweaklets.setDefault(InterceptContributions.KEY,
				new InterceptContributions() {
					@Override
					public IViewPart tweakView(Object viewContribution) {
						return (IViewPart) viewContribution;
					}
					@Override
					public IEditorPart tweakEditor(Object editorContribution) {
						return (IEditorPart) editorContribution;
					}
				});
	}

	public InterceptContributions() {
	}

	public abstract IViewPart tweakView(Object viewContribution);

	public abstract IEditorPart tweakEditor(Object editorContribution);

}
