			text = SWTUtils.createText(composite);

			final Button button = new Button(composite, SWT.NONE);
			button.setText(buttonText);
			button.setLayoutData(new GridData());

			button.addSelectionListener(this);
		}

		public void addModifyListener(ModifyListener listener) {
			text.addModifyListener(listener);
		}

		public void widgetSelected(SelectionEvent e) {
			final ILabelProvider labelProvider = new LabelProvider() {
				public String getText(Object element) {
					return ((Map.Entry) element).getKey()
					+ " - " + ((Map.Entry) element).getValue(); //$NON-NLS-1$
