		public String suggestBranchName() {
			return NLS.bind(UIText.Change_SuggestedBranchNamePattern,
					changeNumber, patchSetNumber);
		}

		public String computeFullRefName() {
			return NLS.bind(UIText.Change_FullRefNamePattern, changeNumber,
					patchSetNumber);
		}

