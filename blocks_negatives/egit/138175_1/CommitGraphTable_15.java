		table.getTable().addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (allCommits != null)
					allCommits.dispose();
				hoverManager.dispose();
			}
		});
