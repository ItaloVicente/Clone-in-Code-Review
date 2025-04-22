				.addPropertyChangeListener(event -> {
if (!event.getOldValue().equals(event.getNewValue())) {
				final KeySequence keySequence = fKeySequenceText.getKeySequence();
				if (!keySequence.isComplete()) {
					return;
				}
