					return Status.OK_STATUS;
				}
			};
			Control previousFocusControl = tree.getControl().getDisplay().getFocusControl();
			if (previousFocusControl == null) {
				previousFocusControl = rootDirectoryText;
