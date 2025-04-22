		if (buttonId == detailButtonID) {
			toggleDetailsArea();
		} else {
			super.buttonPressed(buttonId);
		}
	}

	private void toggleDetailsArea() {
		Point windowSize = getShell().getSize();
		Point oldSize = getContents().computeSize(SWT.DEFAULT, SWT.DEFAULT);

		if (text != null) {
			text.dispose();
			text = null;
			getButton(detailButtonID).setText(
					IDialogConstants.SHOW_DETAILS_LABEL);
		} else {
			createDropDownText((Composite) getContents());
			getButton(detailButtonID).setText(
					IDialogConstants.HIDE_DETAILS_LABEL);
		}

		Point newSize = getContents().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		getShell()
				.setSize(
						new Point(windowSize.x, windowSize.y
								+ (newSize.y - oldSize.y)));
	}

	protected void createDropDownText(Composite parent) {
		text = new Text(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		text.setFont(parent.getFont());

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			detail.printStackTrace(ps);
			ps.flush();
			baos.flush();
			text.setText(baos.toString());
		} catch (IOException e) {
		}

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		data.heightHint = text.getLineHeight() * TEXT_LINE_COUNT;
		data.horizontalSpan = 2;
		text.setLayoutData(data);
	}

	public static boolean openQuestion(Shell parent, String title,
			String message, Throwable detail, int defaultIndex) {
		String[] labels;
		if (detail == null) {
