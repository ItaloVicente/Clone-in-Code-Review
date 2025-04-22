		public String getBranchSuggestion() {
			Object ps = getPatchSetNumber();
			if (ps == null) {
				ps = SIMPLE_TIMESTAMP.format(new Date());
			}
			return MessageFormat.format(
					UIText.FetchGerritChangePage_SuggestedRefNamePattern,
					Long.toString(getChangeNumber()), ps);
