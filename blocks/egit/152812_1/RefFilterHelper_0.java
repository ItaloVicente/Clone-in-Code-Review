			if (expandedFilterPattern == null) {
				expandedFilterPattern = expandMacros();
			}
			return expandedFilterPattern;
		}

		private TokenizedPattern expandMacros() {
