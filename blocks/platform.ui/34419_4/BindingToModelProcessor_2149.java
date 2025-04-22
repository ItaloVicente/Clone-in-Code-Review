package org.eclipse.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Shell;

public abstract class AnimationFeedbackBase {
	private AnimationEngine engine;
	private Shell baseShell;
	private Shell animationShell = null;

	public AnimationFeedbackBase(Shell parentShell) {
		baseShell = parentShell;
		
		baseShell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (engine != null)
					engine.cancelAnimation();
			}
		});
	}

	public abstract void initialize(AnimationEngine animationEngine);

	public abstract void renderStep(AnimationEngine engine);

	public boolean jobInit(AnimationEngine engine) {
		this.engine = engine;
		return engine != null;
	}

	public void dispose() {
		if (animationShell != null && !animationShell.isDisposed())
			animationShell.dispose();
	}

	public Shell getBaseShell() {
		return baseShell;
	}

	public Shell getAnimationShell() {
		if (animationShell == null) {
			animationShell = new Shell(getBaseShell(), SWT.NO_TRIM | SWT.ON_TOP);			
			
			animationShell.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (engine != null)
						engine.cancelAnimation();
				}
			});
		}
		
		return animationShell;
	}

}
