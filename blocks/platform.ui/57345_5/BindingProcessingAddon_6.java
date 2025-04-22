				}
			} else if (elementObj instanceof MBindingTable) {
				if (UIEvents.isADD(event)) {
					for (Object newObj2 : UIEvents.asIterable(event,
							UIEvents.EventTags.NEW_VALUE)) {
						if (newObj2 instanceof MKeyBinding) {
							MKeyBinding binding2 = (MKeyBinding) newObj2;
							updateBinding(binding2, true, elementObj);
