						if (UIEvents.KeyBinding.COMMAND.equals(attrName)) {
							MKeyBinding oldBinding = (MKeyBinding) EcoreUtil
									.copy((EObject) binding);
							oldBinding.setCommand((MCommand) oldObj);
							updateBinding(oldBinding, false,
									((EObject) binding).eContainer());
							updateBinding(binding, true, null);
						} else if (UIEvents.KeySequence.KEYSEQUENCE
								.equals(attrName)) {
							MKeyBinding oldBinding = (MKeyBinding) EcoreUtil
									.copy((EObject) binding);
							oldBinding.setKeySequence((String) oldObj);
							updateBinding(oldBinding, false,
									((EObject) binding).eContainer());
							updateBinding(binding, true, null);
