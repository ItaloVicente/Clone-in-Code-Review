		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				PlatformUI.getWorkbench().getHelpSystem().displaySearch();
			}
		});
