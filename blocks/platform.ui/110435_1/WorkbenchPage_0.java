		IWorkbenchPart wPart = ref.getPart(false);
		MPart part = ((EditorReference)ref).getModel();
		part.setVisible(true);
								
		MElementContainer<MUIElement> partStack = part.getParent();
		partStack.setSelectedElement(null);
		partStack.setSelectedElement(part);
		wPart.setFocus();
