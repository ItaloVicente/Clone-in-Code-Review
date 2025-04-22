			List<MPerspectiveStack> theStack = modelService.findElements(window, null,
					MPerspectiveStack.class, null);
			if (theStack.isEmpty()) {
				return;
			} else if (!theStack.isEmpty() && changedElement != theStack.get(0)) {
				return;
			}
