
		table.getTable().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				allCommits.dispose();
				renderer.dispose();
			}
		});
