			Point contentsSize = contents.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			Point buttonSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT,
					force);
			Point result = new Point(buttonSize.x, Math.max(contentsSize.y,
					buttonSize.y));
			return result;
		}
	}

	private static final int defaultStyle = SWT.NONE;

	public DialogCellEditor() {
		setStyle(defaultStyle);
	}

	protected DialogCellEditor(Composite parent) {
		this(parent, defaultStyle);
	}

	protected DialogCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	protected Button createButton(Composite parent) {
		Button result = new Button(parent, SWT.DOWN);
		result.setText("..."); //$NON-NLS-1$
		return result;
	}

	protected Control createContents(Composite cell) {
		defaultLabel = new Label(cell, SWT.LEFT);
		defaultLabel.setFont(cell.getFont());
		defaultLabel.setBackground(cell.getBackground());
		return defaultLabel;
	}

	@Override
