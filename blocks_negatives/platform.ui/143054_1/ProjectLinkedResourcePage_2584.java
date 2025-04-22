        return pathVariablesGroup.performOk() && linkedResourceEditor.performOk();
    }

    /**
     * Set the widget enabled state
     *
     * @param enableLinking the new widget enabled state
     */
    protected void updateWidgetState(boolean enableLinking) {
        topLabel.setEnabled(enableLinking);
        pathVariablesGroup.setEnabled(enableLinking);
        linkedResourceEditor.setEnabled(enableLinking);
    }
