					Table.class);
			Table table = (Table) widgets.get(0);
			TableItem[] items = table.getItems();
			String workingSetName = null;
			for (WorkingSetDescriptor descriptor : descriptors) {
				if (defaultEditPageClassName.equals(descriptor
						.getPageClassName())) {
					workingSetName = descriptor.getName();
					break;
				}
			}
			assertNotNull(workingSetName);
			boolean found = false;
			for (int i = 0; i < items.length; i++) {
				if (items[i].getText().equals(workingSetName)) {
					table.setSelection(i);
					found = true;
					break;
				}
			}
			assertTrue(found);
			fWizardDialog.showPage(fWizard.getNextPage(page));
		}
		page = fWizardDialog.getCurrentPage();
		assertTrue(page instanceof IWorkingSetPage);

		assertTrue(page.getClass() == defaultEditPage.getClass());
		assertFalse(page.canFlipToNextPage());
		assertFalse(fWizard.canFinish());
		assertNull(page.getErrorMessage());
		assertNull(page.getMessage());

		setTextWidgetText(WORKING_SET_NAME_1, page);
		assertFalse(page.canFlipToNextPage());
		assertTrue(fWizard.canFinish());  // allow for empty sets
		assertNull(page.getErrorMessage());
		assertNotNull(page.getMessage());

		checkTreeItems();
		assertFalse(page.canFlipToNextPage());
		assertTrue(fWizard.canFinish());
		assertNull(page.getErrorMessage());
		assertNull(page.getMessage());

		fWizard.performFinish();
		IWorkingSet workingSet = ((WorkingSetNewWizard) fWizard).getSelection();
		IAdaptable[] workingSetItems = workingSet.getElements();
		assertEquals(WORKING_SET_NAME_1, workingSet.getName());
