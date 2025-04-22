		this.fadeJob = AnimationUtil.fadeOut(getShell(), new IFadeListener() {
			@Override
			public void faded(Shell shell, int alpha) {
				if (!shell.isDisposed()) {
					if (alpha == 0) {
						shell.close();
					} else if (isMouseOver(shell)) {
						if (AbstractNotificationPopup.this.fadeJob != null) {
							AbstractNotificationPopup.this.fadeJob.cancelAndWait(false);
						}
						AbstractNotificationPopup.this.fadeJob = AnimationUtil.fastFadeIn(shell, new IFadeListener() {
							@Override
							public void faded(Shell shell, int alpha) {
								if (shell.isDisposed()) {
									return;
								}

								if (alpha == 255) {
									scheduleAutoClose();
								}
							}
						});
