
		String suggestedName = getBranchNameSuggestion();
		if (suggestedName != null) {
			nameText.setText(suggestedName);
			this.upstreamConfig = getDefaultUpstreamConfig(myRepository,
					this.branchCombo.getText());
		}
		
