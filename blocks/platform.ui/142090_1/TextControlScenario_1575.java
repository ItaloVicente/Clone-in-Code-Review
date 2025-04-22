		Label label = new Label(getComposite(), SWT.NONE);
		getDbc().bindValue(SWTObservables.observeText(text, SWT.FocusOut), SWTObservables.observeText(label));

		text.setText("Frog");
		assertTrue(label.getText().length() == 0);
		text.notifyListeners(SWT.FocusOut, null);
		assertEquals(label.getText(), "Frog");

	}

	@Test
