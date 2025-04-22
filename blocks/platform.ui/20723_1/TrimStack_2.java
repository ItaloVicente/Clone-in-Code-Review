			if (use_overlays) {
				if (clientAreaComposite != null) {
					clientAreaComposite.removeControlListener(caResizeListener);
				}

				hostPane.removeListener(SWT.Traverse, escapeListener);
