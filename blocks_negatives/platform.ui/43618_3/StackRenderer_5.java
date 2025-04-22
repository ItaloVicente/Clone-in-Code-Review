				MPart element = (MPart) menuParent;
				MUIElement parentElement = element.getParent();
				if (parentElement == null) {
					MPlaceholder placeholder = element.getCurSharedRef();
					if (placeholder == null) {
						return;
					}
