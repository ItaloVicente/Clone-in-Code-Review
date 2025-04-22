	@SuppressWarnings("restriction")
	static boolean isCssEngineActive(CTabItem cti) {
		return WidgetElement.getEngine(cti.getParent()) != null;
	}

	static void removeHighlight(MPart part, CTabItem cti, boolean cssEngineActive) {
		part.getTags().remove(CSSConstants.CSS_HIGHLIGHTED_CLASS);
		if (!cssEngineActive) {
			cti.setFont(JFaceResources.getFontRegistry().get(TAB_FONT_KEY));
		}
	}

	static void addHighlight(MPart part, CTabItem cti, boolean cssEngineActive) {
		part.getTags().add(CSSConstants.CSS_HIGHLIGHTED_CLASS);
		if (!cssEngineActive) {
			cti.setFont(JFaceResources.getFontRegistry().getBold(TAB_FONT_KEY));
		}
	}

	static void updateBusyStateNoCss(CTabItem cti, Object newValue, Object oldValue) {
		Font updatedFont = null;
		if (CSSConstants.CSS_BUSY_CLASS.equals(newValue)) {
			updatedFont = JFaceResources.getFontRegistry().getItalic(TAB_FONT_KEY);
		} else if (CSSConstants.CSS_BUSY_CLASS.equals(oldValue)) {
			updatedFont = JFaceResources.getFontRegistry().get(TAB_FONT_KEY);
		}
		if (updatedFont != null) {
			cti.setFont(updatedFont);
		}
	}
