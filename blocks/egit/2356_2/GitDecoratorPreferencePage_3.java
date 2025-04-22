		tabFolder.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if (navigatorPreview != null && changeSetPreview != null) {
					if (UIText.DecoratorPreferencesPage_otherDecorations.equals(e.item.getData())) {
						navigatorPreview.hide();
						changeSetPreview.show();
					} else {
						changeSetPreview.hide();
						navigatorPreview.show();
					}
				}
			}

		});

		changeSetPreview = new ChangeSetPreview(composite);
		navigatorPreview = new NavigatorPreview(composite);

