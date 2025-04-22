        Text text = new Text(parent, SWT.LEFT | SWT.BORDER);
        GridData data = new GridData();
        text.addListener(SWT.Modify, this);
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        data.verticalAlignment = GridData.CENTER;
        data.grabExcessVerticalSpace = false;
        text.setLayoutData(data);
        text.setText(textValue);
