			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					if (FadeJob.this.stopped) {
						return;
					}
