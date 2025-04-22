		List<MStackElement> dropChildren = dropStack.getChildren();
		int elementIndex = dropChildren.indexOf(dragElement);


		MStackElement viewWithSameId = null;
		if (elementIndex == -1 && !dragElement.getTags().contains("Editor")) { //$NON-NLS-1$
			for (MStackElement stackElement : dropChildren) {
				String id = stackElement.getElementId();
				if (id != null && id.equals(dragElement.getElementId())) {
					viewWithSameId = stackElement;
					break;
				}
			}
		}

