			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fTargetPart) {
					fTargetPartVisible = false;
				}
			}

