        getLabelControl(parent);

        valueControl = getValueControl(parent);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL);
        gd.horizontalSpan = numColumns - getNumberOfControls() + 1;
        valueControl.setLayoutData(gd);
        if (previewText != null) {
            previewer = new DefaultPreviewer(previewText, parent);
            gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = previewer.getPreferredExtent();
            gd.widthHint = previewer.getPreferredExtent();
            previewer.getControl().setLayoutData(gd);
        }

        changeFontButton = getChangeControl(parent);
        gd = new GridData();
        int widthHint = convertHorizontalDLUsToPixels(changeFontButton,
                IDialogConstants.BUTTON_WIDTH);
        gd.widthHint = Math.max(widthHint, changeFontButton.computeSize(
                SWT.DEFAULT, SWT.DEFAULT, true).x);
        changeFontButton.setLayoutData(gd);

    }

    @Override
