	public static MPartStack createStack(String id, boolean visible) {
		MPartStack newStack = BasicFactoryImpl.eINSTANCE.createPartStack();
		newStack.setElementId(id);
		newStack.setToBeRendered(visible);
		return newStack;
	}

