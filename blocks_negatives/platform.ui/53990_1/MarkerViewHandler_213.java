		view.getSite().getShell().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				result[0] = view.getSelectedMarkers();
			}
		});
