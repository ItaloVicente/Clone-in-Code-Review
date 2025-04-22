				setPartName(name.getText());
			}
		});

		Label thirdLabel = new Label(composite, SWT.NONE);
		thirdLabel.setText("Content");
		contentDescription = new Text(composite, SWT.BORDER);
		contentDescription.setText(getContentDescription());
		contentDescription.addModifyListener(new ModifyListener() {
			@Override
