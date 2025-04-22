		treePanel.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (reader != null)
					reader.release();
			}
		});
