		Control control = null;
		if ((getStyle() & SWT.SINGLE) != 0 && withBorder()) {
			outer = new Composite(parent, SWT.NONE);
			outer.setVisible(false);
			outer.setLayout(new BorderLayout());
			super.createControl(outer);
			control = outer;
			outer.setBackground(text.getBackground());
			outer.addListener(SWT.Paint, this::drawRectangle);
			text.addListener(SWT.Modify, event -> adjustSize());
		} else {
			control = super.createControl(parent);
		}
