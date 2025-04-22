			final IFile[] file = new IFile[1];
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					Object selectedEdition = getSelectedEdition();
					if (selectedEdition instanceof DiffNode) {
						DiffNode diffNode = (DiffNode) selectedEdition;
						ITypedElement element = diffNode.getLeft();
						if (element instanceof ResourceEditableRevision) {
							ResourceEditableRevision resourceRevision = (ResourceEditableRevision) element;
							file[0] = resourceRevision.getFile();
						}
					}
