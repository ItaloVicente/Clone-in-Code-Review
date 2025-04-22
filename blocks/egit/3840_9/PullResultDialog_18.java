			Control fresult = dlg.createFetchResultTable(fetchResultGroup);
			Object layoutData = fresult.getLayoutData();
			if (layoutData instanceof GridData)
				GridDataFactory.createFrom((GridData) layoutData)
						.hint(SWT.DEFAULT, 130).applyTo(fresult);

