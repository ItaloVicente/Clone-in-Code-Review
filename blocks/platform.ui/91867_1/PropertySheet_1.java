			IWorkbenchPart part = context.getPart();
			if (part != null) {
				partActivated(part);
				selectionChanged(part, context.getSelection());
				return true;
			}
