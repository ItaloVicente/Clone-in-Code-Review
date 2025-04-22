				&& isUIThread()) {
			Object selectedEdition = getSelectedEdition();
			if (selectedEdition instanceof DiffNode) {
				DiffNode diffNode = (DiffNode) selectedEdition;
				ITypedElement element = diffNode.getLeft();
				IResource resource = null;
				if (element instanceof HiddenResourceTypedElement) {
					resource = ((HiddenResourceTypedElement) element)
							.getRealFile();
