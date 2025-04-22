	private MPartStack createStack(String id, boolean visible) {
		MPartStack newStack = modelService.createModelElement(MPartStack.class);
		newStack.setElementId(id);
		newStack.setToBeRendered(visible);
		return newStack;
	}

