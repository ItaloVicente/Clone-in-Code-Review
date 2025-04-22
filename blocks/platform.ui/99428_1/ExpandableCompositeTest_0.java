	@Test
	public void testWrappingClientText() {
		createExtendableComposite(shortText, ExpandableComposite.TWISTIE);
		width(200);
		Composite client = new Composite(ec, SWT.NONE);
		client.setLayout(new TableWrapLayout());
		ec.setClient(client);
		Text text = createText(client, longText);
		ec.setExpanded(true);
		update();
		assertAround("Wrapped Text Width", 190, text.getBounds().width, 1);
	}

	@Test
	public void testWrappingClientLabel() {
		createExtendableComposite(shortText, ExpandableComposite.TWISTIE);
		width(200);
		Composite client = new Composite(ec, SWT.NONE);
		client.setLayout(new TableWrapLayout());
		ec.setClient(client);
		Label label = createLabel(client, longText);
		ec.setExpanded(true);
		update();
		assertAround("Wrapped Label Width", 190, label.getBounds().width, 1);
	}

