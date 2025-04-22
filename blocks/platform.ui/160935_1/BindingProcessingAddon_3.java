			}
		}
		else if (UIEvents.isREMOVE(event)) {
			for (Object oldObj : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				if (oldObj instanceof MKeyBinding) {
					MKeyBinding binding = (MKeyBinding) oldObj;
					updateBinding(binding, false, elementObj);
