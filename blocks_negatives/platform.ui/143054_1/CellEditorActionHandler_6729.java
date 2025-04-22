                activeEditor = null;
                updateActionsEnableState();
                break;
            default:
                break;
            }
        }
    }

    private class ActionEnabledChangeListener implements
            IPropertyChangeListener {
        private IAction actionHandler;

        protected ActionEnabledChangeListener(IAction actionHandler) {
            super();
            this.actionHandler = actionHandler;
        }

        @Override
