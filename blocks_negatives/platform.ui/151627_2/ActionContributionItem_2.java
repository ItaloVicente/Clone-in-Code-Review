				/*
				 * Process accelerators on GTK in a special way to avoid Bug 42009. We will
				 * override the native input method by allowing these reserved accelerators to
				 * be placed on the menu. We will only do this for "Ctrl+Shift+[0-9A-FU]".
				 */
				final String commandId = updatedAction.getActionDefinitionId();
				if ((Util.isGtk()) && (callback instanceof IBindingManagerCallback) && (commandId != null)) {
					final IBindingManagerCallback bindingManagerCallback = (IBindingManagerCallback) callback;
					final IKeyLookup lookup = KeyLookupFactory.getDefault();
					final TriggerSequence[] triggerSequences = bindingManagerCallback.getActiveBindingsFor(commandId);
					for (final TriggerSequence triggerSequence : triggerSequences) {
						final Trigger[] triggers = triggerSequence.getTriggers();
						if (triggers.length == 1) {
							final Trigger trigger = triggers[0];
							if (trigger instanceof KeyStroke) {
								final KeyStroke currentKeyStroke = (KeyStroke) trigger;
								final int currentNaturalKey = currentKeyStroke.getNaturalKey();
								if ((currentKeyStroke.getModifierKeys() == (lookup.getCtrl() | lookup.getShift()))
										&& ((currentNaturalKey >= '0' && currentNaturalKey <= '9')
												|| (currentNaturalKey >= 'A' && currentNaturalKey <= 'F')
												|| (currentNaturalKey == 'U'))) {
									accelerator = currentKeyStroke.getModifierKeys() | currentNaturalKey;
									acceleratorText = triggerSequence.format();
									break;
								}
							}
