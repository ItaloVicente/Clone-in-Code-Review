				} else if (attPopupMenuId != null) {
					NavigatorPlugin
							.logError(
									0,
									"A popupMenuId attribute and popupMenu element may NOT be concurrently specified. (see " + element.getNamespaceIdentifier() + ")", null); //$NON-NLS-1$ //$NON-NLS-2$
				} else if (tagPopupMenu.length > 1) {
					NavigatorPlugin
							.logError(
									0,
									"Only one \"popupMenu\" child of \"viewer\" may be specified. (see " + element.getNamespaceIdentifier() + ")", null); //$NON-NLS-1$ //$NON-NLS-2$
				} else if(tagPopupMenu.length == 1) { // valid case
