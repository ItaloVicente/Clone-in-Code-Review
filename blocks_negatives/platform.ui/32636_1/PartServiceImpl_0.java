		if (lastContainer == null) {
			MPartStack stack = modelService.createModelElement(MPartStack.class);
			searchRoot.getChildren().add(stack);
			return stack;
		} else if (!(lastContainer instanceof MPartStack)) {
			MPartStack stack = modelService.createModelElement(MPartStack.class);
			((List) lastContainer.getChildren()).add(stack);
			return stack;
