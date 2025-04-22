				setTitle(title.getText());
			}
		});

		Label secondLabel = new Label(composite, SWT.NONE);
		secondLabel.setText("Name");
		name = new Text(composite, SWT.BORDER);
		name.setText(getPartName());
		name.addModifyListener(new ModifyListener() {
			@Override
