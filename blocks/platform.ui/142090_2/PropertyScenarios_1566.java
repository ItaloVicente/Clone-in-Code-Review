		Spinner spinner1 = new Spinner(getComposite(), SWT.NONE);
		spinner1.setSelection(10);
		spinner1.setMinimum(1);
		spinner1.setMaximum(100);
		Spinner spinner2 = new Spinner(getComposite(), SWT.NONE);
		spinner2.setMaximum(1);

		getDbc().bindValue(SWTObservables.observeSelection(spinner1), SWTObservables.observeMax(spinner2));

		assertEquals(1, spinner1.getSelection());
		spinner1.setSelection(10);
		spinner1.notifyListeners(SWT.Modify, new Event());
		assertEquals(10, spinner2.getMaximum());
	}

	@Test
