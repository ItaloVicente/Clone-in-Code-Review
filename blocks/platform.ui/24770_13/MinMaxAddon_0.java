			trimId = TrimStackIdHelper.createTrimStackId(element, modelService.getPerspectiveFor(element), null);
			trimStack = (MToolControl) modelService.find(trimId, window);
			if (trimStack == null || trimStack.getObject() == null) {
				if (element instanceof MPerspectiveStack) {
					element.setVisible(true);
				}
				return;
