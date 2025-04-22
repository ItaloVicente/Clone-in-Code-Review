            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new NewWizardDropDownAction(window);
            action.setId(getId());
            return action;
        }
    };
