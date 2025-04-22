
			for (ModelEl modelEl : new ArrayList<ModelEl>(
					sharedElementToRecord.keySet())) {
				MWindow parentWindow = modelService
						.getTopLevelWindowFor(modelEl.getParent());
				if (window == parentWindow) {
					sharedElementToRecord.remove(modelEl);
				}
			}
