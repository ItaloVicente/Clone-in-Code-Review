        super.propagateChange(event);
        String prop = event.getProperty();
        if (prop.equals(IAction.TEXT)) {
            String str = (String) event.getNewValue();
            super.setText(appendAccelerator(str));
        } else if (prop.equals(IAction.TOOL_TIP_TEXT)) {
            String str = (String) event.getNewValue();
            super.setToolTipText(str);
        } else if (prop.equals(IAction.IMAGE)) {
            updateImages(getActionHandler());
        }
    }

    /**
     * Sets the action handler.  Update self.
     */
    @Override
