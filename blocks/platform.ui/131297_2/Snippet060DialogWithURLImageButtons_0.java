
			@Override
			protected Control createContents(Composite parent) {
				Label label = new Label(parent, SWT.CENTER);
				label.setText("Dialog with Url Image Buttons");
				label.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
				return super.createContents(parent);
			}
