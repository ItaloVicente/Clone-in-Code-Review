	public void suppressed_testTableWrapLayoutWrappingLabels() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		shell.setSize(100, 300);
		shell.setLayout(new FillLayout());
		Composite inner = new Composite(shell, SWT.V_SCROLL);
		inner.setLayout(new TableWrapLayout());
		Label l1 = new Label(inner, SWT.WRAP);
		l1.setText(A10);
		Label l2 = new Label(inner, SWT.WRAP);
		l2.setText(A80);
		shell.layout();
		assertTrue(l1.getSize().y < l2.getSize().y);
		assertTrue("Label is too wide for layout ", l1.getSize().x <= 100);
		assertTrue("Label is too wide for layout ", l2.getSize().x <= 100);
		assertTrue("Labels overlap", l2.getBounds().y >= l1.getBounds().y + l1.getBounds().height);
		shell.dispose();
