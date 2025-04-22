	private SashForm createRebasePlanSashForm(final Form parent,
			final FormToolkit toolkit) {
		SashForm sashForm = new SashForm(parent.getBody(), SWT.NONE);
		toolkit.adapt(sashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sashForm);
		return sashForm;
	}

