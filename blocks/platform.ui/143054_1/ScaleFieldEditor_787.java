		return 2;
	}

	public int getPageIncrement() {
		return pageIncrementValue;
	}

	public Scale getScaleControl() {
		return scale;
	}

	private Scale getScaleControl(Composite parent) {
		if (scale == null) {
			scale = new Scale(parent, SWT.HORIZONTAL);
			scale.setFont(parent.getFont());
			scale.addSelectionListener(widgetSelectedAdapter(event -> valueChanged()));
			scale.addDisposeListener(event -> scale = null);
		} else {
			checkParent(scale, parent);
		}
		return scale;
	}

	private void setDefaultValues() {
		setValues(0, 10, 1, 1);
	}

	@Override
