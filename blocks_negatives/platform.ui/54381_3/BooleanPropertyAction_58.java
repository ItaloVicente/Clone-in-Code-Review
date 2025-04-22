                .addPropertyChangeListener(new IPropertyChangeListener() {
                    @Override
					public void propertyChange(PropertyChangeEvent event) {
                        if (finalProprety.equals(event.getProperty())) {
							setChecked(Boolean.TRUE.equals(event.getNewValue()));
						}
                    }
                });
