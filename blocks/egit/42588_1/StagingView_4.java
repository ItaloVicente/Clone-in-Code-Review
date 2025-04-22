	private final class PartListener implements IPartListener2 {
		StructuredSelection lastSelection;

		public void partVisible(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		public void partOpened(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, false);
		}

		public void partHidden(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
		}

		public void partClosed(IWorkbenchPartReference partRef) {
			updateHiddenState(partRef, true);
		}

		public void partActivated(IWorkbenchPartReference partRef) {
			if (isMe(partRef)) {
				if (lastSelection != null) {
					reactOnSelection(lastSelection);
					lastSelection = null;
				}
				return;
			}
			IWorkbenchPart part = partRef.getPart(false);
			StructuredSelection sel = null;
			if (part instanceof IEditorPart) {
				IResource resource = getResource((IEditorPart) part);
				if (resource != null) {
					sel = new StructuredSelection(resource);
				}
			} else {
				ISelection selection = partRef.getPage().getSelection();
				if (selection instanceof StructuredSelection) {
					sel = (StructuredSelection) selection;
				}
			}
			if (isViewHidden) {
				lastSelection = sel;
			} else {
				lastSelection = null;
				if (sel != null) {
					reactOnSelection(sel);
				}
			}

		}

		private IResource getResource(IEditorPart part) {
			IEditorInput input = part.getEditorInput();
			if (input instanceof IFileEditorInput) {
				return ((IFileEditorInput) input).getFile();
			} else {
				return CommonUtils.getAdapter(input, IResource.class);
			}
		}

		private void updateHiddenState(IWorkbenchPartReference partRef,
				boolean hidden) {
			if (isMe(partRef)) {
				isViewHidden = hidden;
			}
		}

		private boolean isMe(IWorkbenchPartReference partRef) {
			return partRef.getPart(false) == StagingView.this;
		}

		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		public void partInputChanged(IWorkbenchPartReference partRef) {
		}
	}

