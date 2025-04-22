		BusyIndicator.showWhile(parent.getDisplay(), new Runnable() {
			@Override
			public void run() {
				createFormContent(mform);
			}
		});
