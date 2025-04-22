		assertEquals(adventure.getMaxNumberOfPeople(), spinner.getSelection());
		spinner.setSelection(5);
		assertEquals(5, adventure.getMaxNumberOfPeople());
		adventure.setMaxNumberOfPeople(7);
		assertEquals(7, spinner.getSelection());
		adventure.setMaxNumberOfPeople(11);
		spinEventLoop(0);
		assertEquals(11, spinner.getSelection());
	}
