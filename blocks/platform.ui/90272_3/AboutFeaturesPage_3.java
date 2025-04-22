		table.addSelectionListener(widgetSelectedAdapter(e -> {
			if (e.item == null)
				return;
			AboutBundleGroupData info = (AboutBundleGroupData) e.item
					.getData();
			updateInfoArea(info);
			updateButtons(info);
		}));
