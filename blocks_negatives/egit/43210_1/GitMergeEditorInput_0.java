			Object selectedEdition = getSelectedEdition();
			if (selectedEdition instanceof DiffNode) {
				DiffNode diffNode = (DiffNode) selectedEdition;
				ITypedElement element = diffNode.getLeft();
				if (element instanceof ResourceEditableRevision) {
					ResourceEditableRevision resourceRevision = (ResourceEditableRevision) element;
					return resourceRevision.getFile();
