		uiSynchronize.syncExec(new Runnable() {
			@Override
			public void run() {
				BusyIndicator.showWhile(getDisplay(), runnableWithStatus);
			}

		});
