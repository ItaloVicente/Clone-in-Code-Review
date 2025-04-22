            themeListener = new IPropertyChangeListener() {

                @Override
				public void propertyChange(PropertyChangeEvent event) {
                    firePropertyChange(event);
                }
            };
