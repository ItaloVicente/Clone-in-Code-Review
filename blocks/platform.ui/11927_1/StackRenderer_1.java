				if (CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(newValue)) {
					part.getTags()
							.remove(CSSConstants.CSS_CONTENT_CHANGE_CLASS);
					if (cti != cti.getParent().getSelection()) {
						part.getTags().add(CSSConstants.CSS_HIGHLIGHTED_CLASS);
					}
				} else if (CSSConstants.CSS_ACTIVE_CLASS.equals(newValue)
						&& part.getTags().contains(
								CSSConstants.CSS_HIGHLIGHTED_CLASS)) {
					part.getTags().remove(CSSConstants.CSS_HIGHLIGHTED_CLASS);
