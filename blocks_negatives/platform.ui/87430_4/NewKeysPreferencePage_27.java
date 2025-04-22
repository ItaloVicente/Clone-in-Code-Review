				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public final void propertyChange(
							final PropertyChangeEvent event) {
						if (!event.getOldValue().equals(event.getNewValue())) {
							final KeySequence keySequence = fKeySequenceText.getKeySequence();
							if (!keySequence.isComplete()) {
								return;
							}
