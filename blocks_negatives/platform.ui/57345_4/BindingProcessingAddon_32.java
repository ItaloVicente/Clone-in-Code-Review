				} else if (elementObj instanceof MKeyBinding) {
					MKeyBinding binding = (MKeyBinding) elementObj;

					String attrName = (String) event
							.getProperty(UIEvents.EventTags.ATTNAME);

					if (UIEvents.isSET(event)) {
						Object oldObj = event
