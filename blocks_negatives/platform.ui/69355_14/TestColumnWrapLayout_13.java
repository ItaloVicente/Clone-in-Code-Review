		inner.setLayout(layout);
		new SizedComposite(inner, SWT.NULL, 30);
		new SizedComposite(inner, SWT.NULL, 40);
		new SizedComposite(inner, SWT.NULL, 20);
		shell.layout(true);
		assertEquals(70, inner.getSize().y);
		shell.dispose();
