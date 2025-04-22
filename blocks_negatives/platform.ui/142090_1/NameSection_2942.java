        nameText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
        nameText.setEditable(false);
        data = new FormData();
        data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(0, 0);
        nameText.setLayoutData(data);
