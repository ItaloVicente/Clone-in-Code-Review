			if (UIEvents.isADD(event)) {
				if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE, MINIMIZED)) {
					minimize(changedElement);
				} else if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE, MAXIMIZED)) {
					maximize(changedElement);
				}
			} else if (UIEvents.isREMOVE(event)) {
				if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE, MINIMIZED)) {
					restore(changedElement);
				} else if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE, MAXIMIZED)) {
					unzoom(changedElement);
				}
			}
