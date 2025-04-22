		BusyIndicator.showWhile(null, new Runnable() {
			@Override
			public void run() {
				try {
					result[0] = busyShowView(compoundId, mode);
				} catch (PartInitException e) {
					result[0] = e;
				}
