		Cart cart = SampleData.CART;
		cart.setAdventureDays(42);
		Text text = new Text(getComposite(), SWT.BORDER);

		System.out.println("Expecting message about not being able to attach a listener");
		getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify),
				BeansObservables.observeValue(cart, "lodgingDays"));

		assertEquals(Integer.valueOf(cart.getLodgingDays()).toString(), text.getText());
	}

	@Test
