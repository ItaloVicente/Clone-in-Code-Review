		getDbc().bindValue(SWTObservables.observeSelection(button),
				BeansObservables.observeValue(adventure, "petsAllowed"));

		assertEquals(button.getSelection(), adventure.isPetsAllowed());
		boolean newBoolean = !adventure.isPetsAllowed();
		adventure.setPetsAllowed(newBoolean);
		assertEquals(newBoolean, adventure.isPetsAllowed());
		assertEquals(button.getSelection(), newBoolean);
		newBoolean = !newBoolean;
		button.setSelection(newBoolean);
		button.notifyListeners(SWT.Selection, null);
		assertEquals(newBoolean, adventure.isPetsAllowed());
		newBoolean = !newBoolean;
		final boolean finalNewBoolean = newBoolean;
		adventure.setPetsAllowed(finalNewBoolean);
		spinEventLoop(0);
		assertEquals(newBoolean, button.getSelection());

	}

	@SuppressWarnings("unchecked")
	@Test
