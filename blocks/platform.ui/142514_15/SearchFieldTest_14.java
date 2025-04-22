		Event event = new Event();
		event.type = SWT.MouseUp;
		event.widget = text;
		text.notifyListeners(SWT.MouseUp, event);
		assertNotEquals("Quick access dialog should be visible now", Optional.empty(),
				QuickAccessDialogTest.findQuickAccessDialog());
