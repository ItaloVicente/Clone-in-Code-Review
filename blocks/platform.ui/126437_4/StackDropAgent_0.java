		List<MStackElement> dropChildren = dropStack.getChildren();
		int elementIndex = dropChildren.indexOf(dragElement);

		MStackElement childWithSameId = null;
		if (elementIndex == -1) {
			for (MStackElement stackElement : dropChildren) {
				String id = stackElement.getElementId();
				if (id != null && id.equals(dragElement.getElementId())) {
					childWithSameId = stackElement;
					break;
				}
			}
		}

