				filterText.addModifyListener(e -> {
					filterPattern = wildcardToRegex(filterText.getText());
					StagingViewSearchThread searchThread = new StagingViewSearchThread(
							StagingView.this);
					filterText.getDisplay().timerExec(200,
							() -> searchThread.start());
