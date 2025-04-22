			menuItem.addSelectionListener(widgetSelectedAdapter(e -> {
				textTriggerSequenceManager.insert(trappedKey);
				textTriggerSequence.setFocus();
				textTriggerSequence.setSelection(textTriggerSequence
						.getTextLimit());
			}));
