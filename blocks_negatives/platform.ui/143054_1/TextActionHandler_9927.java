            if (event.getProperty().equals(IAction.ENABLED)) {
                Boolean bool = (Boolean) event.getNewValue();
                actionHandler.setEnabled(bool.booleanValue());
            }
        }
    }

    private class DeleteActionHandler extends Action {
        protected DeleteActionHandler() {
            super(IDEWorkbenchMessages.Delete);
            setEnabled(false);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
