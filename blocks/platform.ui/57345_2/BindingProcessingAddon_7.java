				}
				else if (UIEvents.isREMOVE(event)) {
					for (Object oldObj1 : UIEvents.asIterable(event,
							UIEvents.EventTags.OLD_VALUE)) {
						if (oldObj1 instanceof MKeyBinding) {
							MKeyBinding binding3 = (MKeyBinding) oldObj1;
							updateBinding(binding3, false, elementObj);
