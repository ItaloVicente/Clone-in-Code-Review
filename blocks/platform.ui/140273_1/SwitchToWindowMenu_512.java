			if (!window.getShell().isDisposed()) {
				String name = calcText(count, window);
				if (name != null) {
					MenuItem mi = new MenuItem(menu, SWT.RADIO, index);
					index++;
					count++;
					mi.setText(name);
					mi.addSelectionListener(widgetSelectedAdapter(e -> {
						Shell windowShell = window.getShell();
						if (windowShell.getMinimized()) {
