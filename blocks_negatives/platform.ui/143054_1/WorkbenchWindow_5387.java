		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				try {
					result[0] = busyOpenPage(perspectiveId, input);
				} catch (WorkbenchException e) {
					result[0] = e;
				}
