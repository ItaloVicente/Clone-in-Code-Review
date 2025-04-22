			if (SWT_SELECTED_TABS_BACKGROUND.equals(property)) {
				((ICTabRendering) renderer).setSelectedTabFill(newColor);
			} else if (SWT_ACTIVE_TAB_HIGHLIGHT.equals(property)) {
				if ("none".equalsIgnoreCase(value.getCssText()) || "transparent".equalsIgnoreCase(value.getCssText())) {
					((ICTabRendering) renderer).setSelectedTabHighlight(null);
				} else {
					((ICTabRendering) renderer).setSelectedTabHighlight(newColor);
				}
			}
