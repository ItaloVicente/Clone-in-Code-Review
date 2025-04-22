		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!animationCanceled)
					animationCanceled = !feedbackRenderer
							.jobInit(AnimationEngine.this);
			}
