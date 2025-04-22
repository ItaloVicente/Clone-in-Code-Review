		display.syncExec(new Runnable() {
			@Override
			public void run() {
				tWork[0] = measureAvgTimePerWorkItem();
			}
		});
