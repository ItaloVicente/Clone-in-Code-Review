					Table.class);
			Table table = (Table) widgets.get(0);
			assertEquals(descriptors.length, table.getItemCount());
			assertTrue(typePage.canFlipToNextPage() == false);
			assertTrue(fWizard.canFinish() == false);
			table.setSelection(descriptors.length - 1);
			table.notifyListeners(SWT.Selection, new Event());
			assertTrue(typePage.canFlipToNextPage());
			assertTrue(fWizard.canFinish() == false);

