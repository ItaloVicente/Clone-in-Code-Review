		this.fadeJob = AnimationUtil.fadeIn(this.shell, new IFadeListener() {
			@Override
			public void faded(Shell shell, int alpha) {
				if (shell.isDisposed()) {
					return;
				}
