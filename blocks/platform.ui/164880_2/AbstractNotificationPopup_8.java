					AbstractNotificationPopup.this.fadeJob = AnimationUtil.fastFadeIn(shell, (shell1, alpha1) -> {
						if (shell1.isDisposed()) {
							return;
						}

						if (alpha1 == 255) {
							scheduleAutoClose();
						}
					});
