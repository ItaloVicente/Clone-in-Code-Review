		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				loadedCommits.dispose();
			}
		});
