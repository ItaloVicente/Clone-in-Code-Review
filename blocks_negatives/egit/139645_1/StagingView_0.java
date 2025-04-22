				final Display display = Display.getCurrent();
				filterText.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						filterPattern = wildcardToRegex(filterText.getText());
						final StagingViewSearchThread searchThread = new StagingViewSearchThread(
								StagingView.this);
						display.timerExec(200, new Runnable() {
							@Override
							public void run() {
								searchThread.start();
							}
						});
					}
