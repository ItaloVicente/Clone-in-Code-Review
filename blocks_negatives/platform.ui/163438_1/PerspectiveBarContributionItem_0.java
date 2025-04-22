				if (apiPreferenceStore.getString(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR)
						.equals(IWorkbenchPreferenceConstants.TOP_LEFT)) {
					toolItem.setText(perspective.getLabel());
				} else {
					toolItem.setText(shortenText(perspective.getLabel(), toolItem));
				}
