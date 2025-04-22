	@SuppressWarnings("cast")
	@Test
	public void handlerControlIsText() {
		this.page.createContents(this.page.getShell());

		assertTrue(this.page.handlerLocation instanceof Text);
		assertEquals(SWT.READ_ONLY, this.page.handlerLocation.getStyle() & SWT.READ_ONLY);
	}

