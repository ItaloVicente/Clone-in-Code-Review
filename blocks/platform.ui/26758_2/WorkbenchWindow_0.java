
				if (QUICK_ACCESS_ID.equals(quickAccessElement.getElementId())) {
					if (model.getTags().contains(QUICK_ACCESS_HIDDEN)) {
						if (!quickAccessElement.getTags().contains(
								IPresentationEngine.HIDDEN_EXPLICITLY)) {
							quickAccessElement.getTags().add(IPresentationEngine.HIDDEN_EXPLICITLY);
						}
					}
				}
