            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new OpenPreferencesAction(window);
            action.setId(getId());
            return action;
        }
    };
