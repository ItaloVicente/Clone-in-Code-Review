		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				ret[0] = busyClose(remove);
			}
		});
