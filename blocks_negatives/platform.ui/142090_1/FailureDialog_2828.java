        Composite composite = (Composite) super.createDialogArea(parent);
        composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        Label label = new Label(composite, SWT.WRAP);
        label.setText("&Enter a note regarding the failure:");

        _text = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL
                | SWT.V_SCROLL);
        _text.setFont(JFaceResources.getFontRegistry().get(
                JFaceResources.TEXT_FONT));
        GridData data = new GridData(GridData.FILL_BOTH);
        data.widthHint = SIZING_TEXT_WIDTH;
        data.heightHint = SIZING_TEXT_HEIGHT;
        _text.setLayoutData(data);

        return composite;
    }

    @Override
