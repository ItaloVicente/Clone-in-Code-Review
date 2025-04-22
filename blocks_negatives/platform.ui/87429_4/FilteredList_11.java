		fList.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				fLabelProvider.dispose();
				if (fUpdateJob != null) {
					fUpdateJob.cancel();
				}
