			colorSelector.addListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					ColorFieldEditor.this.fireValueChanged(event.getProperty(),
							event.getOldValue(), event.getNewValue());
					setPresentsDefaultValue(false);
				}
