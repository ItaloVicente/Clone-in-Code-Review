					MPlaceholder placeholder = element.getCurSharedRef();
					if (placeholder == null) {
						return;
					}

					parentElement = placeholder.getParent();
					if (parentElement == null) {
						return;
					}
