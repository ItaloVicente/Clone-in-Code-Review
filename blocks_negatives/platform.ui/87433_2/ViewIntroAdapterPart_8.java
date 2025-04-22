            introPart.addPropertyListener(new IPropertyListener() {
                @Override
				public void propertyChanged(Object source, int propId) {
                    firePropertyChange(propId);
                }
            });
