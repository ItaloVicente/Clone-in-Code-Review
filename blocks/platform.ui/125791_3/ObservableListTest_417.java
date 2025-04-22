		list.addListChangeListener(new IListChangeListener() {
			@Override
			public void handleListChange(ListChangeEvent event) {
				diffEntries.addAll(Arrays.asList(event.diff.getDifferences()));
			}
		});
