		this.fadeJob = AnimationUtil.fadeOut(getShell(), (shell, alpha) -> {
			if (!shell.isDisposed()) {
				if (alpha == 0) {
					shell.close();
				} else if (isMouseOver(shell)) {
					if (AbstractNotificationPopup.this.fadeJob != null) {
						AbstractNotificationPopup.this.fadeJob.cancelAndWait(false);
