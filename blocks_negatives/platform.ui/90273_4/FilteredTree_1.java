			filterText.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					if (e.detail == SWT.ICON_CANCEL)
						clearText();
				}
			});
