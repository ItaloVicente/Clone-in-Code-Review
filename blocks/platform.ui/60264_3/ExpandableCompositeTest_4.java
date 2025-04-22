
	private void width500() {
		GridData layoutData = new GridData();
		layoutData.widthHint = 500;
		ec.setLayoutData(layoutData);
	}

	@Test
	public void testLabelLong() {
		createExtendableComposite(longText, 0);
		width500();
		Rectangle bounds = update();
		assertEquals(500, bounds.width);
		assertTextLines(3, bounds);
	}

	@Test
	public void testLinkLong() {
		createExtendableComposite(longText, ExpandableComposite.FOCUS_TITLE);
		width500();
		Rectangle bounds = update();
		assertTrue(bounds.width < 500);
		assertTextLines(3, bounds);
	}

	private void assertTextLines(int lines, Rectangle bounds) {
		Point textExtend = getTextExtend(shortText);
		assertTrue(textExtend.y * lines <= bounds.height && bounds.height < textExtend.y * (lines + 1));
	}

	private Label createLabel(Composite comp, String text) {
		Label l = new Label(comp, SWT.WRAP);
		l.setText(text);
		return l;
	}

	@Test
	public void testLabelLongAndTextClientLabel() {
		createExtendableComposite(longText, 0);
		width500();

		Label client = createLabel(ec, longText);
		ec.setTextClient(client);

		Rectangle bounds = update();
		assertEquals(500, bounds.width);
		assertEquals(500 / 2 - 3, client.getBounds().width);
		assertTextLines(8, bounds);
	}

	private Composite createComposite(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_MAGENTA));
		return comp;
	}

	private Composite createFillComp(Composite parent) {
		Composite comp = createComposite(parent);
		comp.setLayout(GridLayoutFactory.fillDefaults().create());
		Label l = createLabel(comp, longText);
		l.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.BEGINNING).create());
		return comp;
	}

	private Composite createFixedComp(Composite parent) {
		Composite comp = createComposite(parent);
		comp.setLayout(GridLayoutFactory.fillDefaults().create());
		Label l = createLabel(comp, shortText);
		l.setLayoutData(GridDataFactory.swtDefaults().create());
		return comp;
	}

	@Test
	public void testLabelLongAndTextClientComp() {
		createExtendableComposite(longText, 0);
		width500();

		Control client = createFillComp(ec);
		ec.setTextClient(client);

		Rectangle bounds = update();

		assertEquals(500, bounds.width);
		assertEquals(500 / 2 - 3, client.getBounds().width);
		assertTextLines(8, bounds);
	}

	@Test
	public void testLabelLongAndTextClientCompFixed() {
		createExtendableComposite(longText, 0);
		width500();

		Control client = createFixedComp(ec);
		ec.setTextClient(client);

		Rectangle bounds = update();

		int w = getTextExtend(shortText).x;

		assertEquals(w, client.getBounds().width);
		assertTextLines(4, bounds);
	}

	@Test
	public void testLabelLongAndTextClientCompFixedL() {
		createExtendableComposite(longText, ExpandableComposite.LEFT_TEXT_CLIENT_ALIGNMENT);
		width500();

		Control client = createFixedComp(ec);
		ec.setTextClient(client);

		Rectangle bounds = update();

		int w = getTextExtend(shortText).x;
		assertEquals(w + 8, client.getBounds().width); // not sure +8
		assertTextLines(4, bounds);
	}
