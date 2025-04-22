		List<MPerspectiveStack> ps = modelService.findElements(model, null,
				MPerspectiveStack.class, null);
		MPerspective curPersp = null;
		boolean newWindow = true;
		if (ps.size() > 0) {
			MPerspectiveStack stack = ps.get(0);
			if (stack.getSelectedElement() != null) {
				curPersp = stack.getSelectedElement();
				IPerspectiveDescriptor thePersp = getWorkbench().getPerspectiveRegistry()
						.findPerspectiveWithId(curPersp.getElementId());
				if (thePersp != null) {
					perspective = thePersp;
					newWindow = false;
				}
			}
		}
