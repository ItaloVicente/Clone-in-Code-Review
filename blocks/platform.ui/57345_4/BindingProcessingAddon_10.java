				}
				else if (UIEvents.ApplicationElement.TAGS.equals(attrName)) {
					List<String> tags = binding4.getTags();
					if (tags.contains(EBindingService.DELETED_BINDING_TAG)) {
						updateBinding(binding4, false, elementObj);
					}
					else {
						updateBinding(binding4, true, elementObj);
