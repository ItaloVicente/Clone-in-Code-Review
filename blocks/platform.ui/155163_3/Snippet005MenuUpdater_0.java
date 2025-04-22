		IObservableValue<Boolean> visible = WidgetProperties.visible().observe(submenu);

		ISideEffect.create(() -> {
			ObservableTracker.getterCalled(menuItemStrings);
			if (!visible.getValue()) {
				return;
			}

			System.out.println("updating menu");
			MenuItem[] items = submenu.getItems();
			int itemIndex = 0;

			for (String s : menuItemStrings) {
				MenuItem item;
				if (itemIndex < items.length) {
					item = items[itemIndex++];
				} else {
					item = new MenuItem(submenu, SWT.NONE);
