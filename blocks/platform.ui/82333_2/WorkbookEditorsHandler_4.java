				String matchName = null;
				if (element instanceof EditorReference) {
					matchName = ((EditorReference) element).getTitle();
				}
				if (matchName == null) {
					return false;
				}
				return matcher.matches(matchName);
