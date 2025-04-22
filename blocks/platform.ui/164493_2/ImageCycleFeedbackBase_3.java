package org.eclipse.ui.internal;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FaderAnimationFeedback extends AnimationFeedbackBase {
	private Image backingStore;

	public FaderAnimationFeedback(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (!backingStore.isDisposed())
			backingStore.dispose();
	}

	@Override
	public void initialize(AnimationEngine engine) {
		Rectangle psRect = getBaseShell().getBounds();
		getAnimationShell().setBounds(psRect);

		Display display = getBaseShell().getDisplay();
		backingStore = new Image(display, psRect);
		GC gc = new GC(display);
		gc.copyArea(backingStore, psRect.x, psRect.y);
		gc.dispose();

		getAnimationShell().setAlpha(254);
		getAnimationShell().setBackgroundImage(backingStore);
		getAnimationShell().setVisible(true);
	}

	@Override
	public void renderStep(AnimationEngine engine) {
		getAnimationShell().setAlpha((int) (255 - (engine.amount() * 255)));
	}

}
