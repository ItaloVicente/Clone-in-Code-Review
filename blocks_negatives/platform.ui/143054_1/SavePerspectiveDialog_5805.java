        if (persp != null) {
            perspName = persp.getLabel();
            text.setText(perspName);
        }

        updateButtons();
    }

    /**
     * Sets the initial selection in this dialog.
     *
     * @param selectedElement the perspective descriptor to select
     */
    public void setInitialSelection(IPerspectiveDescriptor selectedElement) {
        initialSelection = selectedElement;
    }

    /**
     * Update the OK button.
     */
    private void updateButtons() {
        if (okButton != null) {
            String label = text.getText();
            okButton.setEnabled(perspReg.validateLabel(label));
        }
    }
