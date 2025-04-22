				}
			} else if (elementObj instanceof MKeyBinding) {
				MKeyBinding binding4 = (MKeyBinding) elementObj;

				String attrName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);

				if (UIEvents.isSET(event)) {
					Object oldObj2 = event
							.getProperty(UIEvents.EventTags.OLD_VALUE);
					if (UIEvents.KeyBinding.COMMAND.equals(attrName)) {
						MKeyBinding oldBinding1 = (MKeyBinding) EcoreUtil
								.copy((EObject) binding4);
						oldBinding1.setCommand((MCommand) oldObj2);
						updateBinding(oldBinding1, false,
								((EObject) binding4).eContainer());
						updateBinding(binding4, true, null);
					} else if (UIEvents.KeySequence.KEYSEQUENCE
							.equals(attrName)) {
						MKeyBinding oldBinding2 = (MKeyBinding) EcoreUtil
								.copy((EObject) binding4);
						oldBinding2.setKeySequence((String) oldObj2);
						updateBinding(oldBinding2, false,
								((EObject) binding4).eContainer());
						updateBinding(binding4, true, null);
					}
				} else if (UIEvents.KeyBinding.PARAMETERS.equals(attrName)) {
					if (UIEvents.isADD(event)) {
						Object newObj3 = event
								.getProperty(UIEvents.EventTags.NEW_VALUE);
						MKeyBinding oldBinding3 = (MKeyBinding) EcoreUtil
								.copy((EObject) binding4);
						if (UIEvents.EventTypes.ADD_MANY.equals(event
								.getProperty(UIEvents.EventTags.TYPE))) {
							oldBinding3.getParameters().removeAll(
									(Collection<?>) newObj3);
						} else {
							oldBinding3.getParameters().remove(newObj3);
						}
						updateBinding(oldBinding3, false,
								((EObject) binding4).eContainer());
						updateBinding(binding4, true, null);
					} else if (UIEvents.isREMOVE(event)) {
						Object oldObj3 = event
