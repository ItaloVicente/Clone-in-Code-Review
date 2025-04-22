
			if (SWT_SELECTED_TAB_HIGHLIGHT.equals(property)) {
				if ("none".equalsIgnoreCase(value.getCssText()) || "transparent".equalsIgnoreCase(value.getCssText())) {
					((ICTabRendering) renderer).setSelectedTabHighlight(null);
				} else {
					((ICTabRendering) renderer).setSelectedTabHighlight(newColor);
				}
			} else if (SWT_SELECTED_HIGHLIGHT_TOP.equals(property)) {
				Boolean drawHiglightOnTop = (Boolean) engine.convert(value, Boolean.class, control.getDisplay());
				((ICTabRendering) renderer).setSelectedTabHighlightTop(drawHiglightOnTop);
			} else {
				((ICTabRendering) renderer).setSelectedTabFill(newColor);
			}
