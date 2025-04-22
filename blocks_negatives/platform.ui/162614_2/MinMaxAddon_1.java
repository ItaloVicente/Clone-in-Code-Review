		Shell hostShell = (Shell) win.getWidget();
		if (hostShell != null) {
			FaderAnimationFeedback fader = new FaderAnimationFeedback(hostShell);
			AnimationEngine engine = new AnimationEngine(win.getContext(), fader, 300);
			engine.schedule();
		}

