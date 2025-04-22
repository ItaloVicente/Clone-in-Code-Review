		} else if (switcherControl != null) {
			if (!getWindowConfigurer().getShowPerspectiveBar()) {
				trimBar.getChildren().remove(switcherControl);
			} else {
				List<String> tags = switcherControl.getTags();
				if (!tags.contains("HIDEABLE")) { //$NON-NLS-1$
					tags.add("HIDEABLE"); //$NON-NLS-1$
				}
				if (!tags.contains("SHOW_RESTORE_MENU")) { //$NON-NLS-1$
					tags.add("SHOW_RESTORE_MENU"); //$NON-NLS-1$
				}
			}
