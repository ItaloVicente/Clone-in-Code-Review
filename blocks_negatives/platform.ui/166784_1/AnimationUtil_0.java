			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					if (setAlpha) {
						FadeJob.this.shell.setAlpha(getLastAlpha());
					}
