			if (isMe(partRef)) {
				if (lastSelection != null) {
					reactOnSelection(lastSelection);
					lastSelection = null;
				}
				return;
			}
			IWorkbenchPart part = partRef.getPart(false);
			StructuredSelection sel = getSelectionOfPart(part);
			if (isViewHidden) {
				lastSelection = sel;
			} else {
