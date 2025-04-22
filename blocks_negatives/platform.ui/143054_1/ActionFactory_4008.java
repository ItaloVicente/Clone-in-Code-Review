            if (window == null) {
                throw new IllegalArgumentException();
            }
            IWorkbenchAction action = new NavigationHistoryAction(window, true);
            action.setId(getId());
            return action;
        }
    };
