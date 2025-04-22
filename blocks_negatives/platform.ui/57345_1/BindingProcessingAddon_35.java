					else if (UIEvents.ApplicationElement.TAGS.equals(attrName)) {
						List<String> tags = binding.getTags();
						if (tags.contains(EBindingService.DELETED_BINDING_TAG)) {
							updateBinding(binding, false, elementObj);
						}
						else {
							updateBinding(binding, true, elementObj);
						}
