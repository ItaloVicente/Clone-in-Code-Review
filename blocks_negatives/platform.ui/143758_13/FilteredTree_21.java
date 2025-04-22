		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
			filterText.addSelectionListener(widgetDefaultSelectedAdapter(e -> {
				if (e.detail == SWT.ICON_CANCEL)
					clearText();
			}));
		}

