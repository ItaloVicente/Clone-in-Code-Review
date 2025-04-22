
			for (ModelEl modelEl : new ArrayList<ModelEl>(
					sharedElementToRecord.keySet())) {
				MWindow parentWindow = modelService
						.getTopLevelWindowFor(modelEl);
				if (window == parentWindow) {
					sharedElementToRecord.remove(modelEl);
				}
			}
