				} else if (elementObj instanceof MBindingTable) {
					if (UIEvents.isADD(event)) {
						for (Object newObj : UIEvents.asIterable(event,
								UIEvents.EventTags.NEW_VALUE)) {
							if (newObj instanceof MKeyBinding) {
								MKeyBinding binding = (MKeyBinding) newObj;
								updateBinding(binding, true, elementObj);
							}
