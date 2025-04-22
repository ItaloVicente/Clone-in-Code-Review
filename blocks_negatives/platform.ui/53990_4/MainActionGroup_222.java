        IPropertyChangeListener workingSetUpdater = new IPropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent event) {
                String property = event.getProperty();

                if (WorkingSetFilterActionGroup.CHANGE_WORKING_SET
                        .equals(property)) {
                    IResourceNavigator navigator = getNavigator();
                    Object newValue = event.getNewValue();

                    if (newValue instanceof IWorkingSet) {
                        navigator.setWorkingSet((IWorkingSet) newValue);
                    } else if (newValue == null) {
                        navigator.setWorkingSet(null);
                    }
                }
            }
        };
