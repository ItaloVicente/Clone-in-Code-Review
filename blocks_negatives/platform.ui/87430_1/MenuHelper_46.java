				final IPropertyChangeListener propListener = new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (IAction.CHECKED.equals(event.getProperty())) {
							boolean checked = false;
							if (event.getNewValue() instanceof Boolean) {
								checked = ((Boolean) event.getNewValue()).booleanValue();
							}
							model.setSelected(checked);
