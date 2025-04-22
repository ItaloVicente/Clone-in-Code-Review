		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item == null)
					return;
				AboutBundleGroupData info = (AboutBundleGroupData) e.item
						.getData();
				updateInfoArea(info);
				updateButtons(info);
			}
		});
