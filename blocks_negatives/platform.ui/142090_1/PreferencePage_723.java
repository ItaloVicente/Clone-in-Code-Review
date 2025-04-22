        noteLabel
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        Label messageLabel = new Label(messageComposite, SWT.WRAP);
        messageLabel.setText(message);
        messageLabel.setFont(font);
        return messageComposite;
    }

    /**
     * Returns the Apply button.
     *
     * @return the Apply button
     */
    protected Button getApplyButton() {
        return applyButton;
    }

    /**
     * Returns the Restore Defaults button.
     *
     * @return the Restore Defaults button
     */
    protected Button getDefaultsButton() {
        return defaultsButton;
    }

    @Override
