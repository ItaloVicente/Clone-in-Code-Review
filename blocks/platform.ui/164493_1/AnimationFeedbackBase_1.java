package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.tweaklets.Animations;
import org.eclipse.ui.internal.tweaklets.Tweaklets;
import org.eclipse.ui.internal.util.PrefUtil;

public class AnimationEngine extends Job {
	public static final int TICK_TIMER = 1;
	public static final int FRAME_COUNT = 2;
	public static final int unlimitedDuration = -1;

	private boolean enableAnimations;
	private long startTime;
	private long curTime;
	private long prevTime;
	private int timingStyle = TICK_TIMER;
	private long frameCount;
	private boolean animationCanceled = false;
	private long sleepAmount;

	private Display display;
	private AnimationFeedbackBase feedbackRenderer;
	private int duration;

	public AnimationEngine(AnimationFeedbackBase animationFeedback, int durationIn) {
		this(animationFeedback, durationIn, 0);
	}

	public AnimationEngine(AnimationFeedbackBase animationFeedback, int durationIn, long sleepAmountIn) {
		super(WorkbenchMessages.RectangleAnimation_Animating_Rectangle);
		sleepAmount = sleepAmountIn;
		feedbackRenderer = animationFeedback;
		duration = durationIn;

		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		enableAnimations = preferenceStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS);
		if (!enableAnimations) {
			return;
		}

		animationCanceled = false;

		display = feedbackRenderer.getAnimationShell().getDisplay();

		animationFeedback.getAnimationShell().addDisposeListener(e -> cancelAnimation());

		setSystem(true);

		feedbackRenderer.initialize(this);

		curTime = startTime = System.currentTimeMillis();

	}

	public AnimationFeedbackBase getFeedback() {
		return feedbackRenderer;
	}

	private Runnable animationStep = () -> {
		if (animationCanceled)
			return;

		prevTime = curTime;
		curTime = System.currentTimeMillis();

		if (isUpdateStep()) {
			updateDisplay();
			frameCount++;
		}
	};

	protected void updateDisplay() {
		if (animationCanceled)
			return;

		feedbackRenderer.renderStep(this);
	}

	protected boolean isUpdateStep() {
		if (duration == unlimitedDuration || timingStyle == FRAME_COUNT) {
			return true;
		}

		return prevTime != curTime;
	}

	private boolean done() {
		return animationCanceled || amount() >= 1.0;
	}

	public double amount() {
		if (duration == unlimitedDuration) {
			return 0;
		}
		double amount = 0.0;
		switch (timingStyle) {
		case TICK_TIMER:
			amount = (double) (curTime - startTime) / (double) duration;
			break;

		case FRAME_COUNT:
			amount = (double) frameCount / (double) duration;
		}

		if (amount > 1.0)
			amount = 1.0;

		return amount;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (!enableAnimations) {
			return Status.OK_STATUS;
		}

		display.syncExec(() -> {
			if (!animationCanceled)
				animationCanceled = !feedbackRenderer.jobInit(AnimationEngine.this);
		});

		if (animationCanceled)
			return Status.CANCEL_STATUS;

		curTime = startTime = System.currentTimeMillis();

		while (!done() && !animationCanceled) {
			display.syncExec(animationStep);

			try {
				Thread.sleep(sleepAmount);
			} catch (InterruptedException e) {
			}
		}

		if (animationCanceled)
			return Status.CANCEL_STATUS;

		display.syncExec(() -> feedbackRenderer.dispose());

		return Status.OK_STATUS;
	}

	public void cancelAnimation() {
		animationCanceled = true;
		feedbackRenderer.dispose();
		cancel();
	}

	public long getFrameCount() {
		return frameCount;
	}

	public static void createTweakedAnimation(Shell shell, int duration, Rectangle start, Rectangle end) {
		RectangleAnimationFeedbackBase feedback = ((Animations) Tweaklets.get(Animations.KEY)).createFeedback(shell);
		feedback.addStartRect(start);
		feedback.addEndRect(end);

		AnimationEngine animation = new AnimationEngine(feedback, 400);
		animation.schedule();
	}
}
