		}, (pattern, ref) -> {
			if (pattern == null || pattern
					.matcher(ref.getChangeNumber().toString()).matches()) {
				return new ChangeContentProposal(ref);
			}
			return null;
		}, s -> {
			String input = s;
			Matcher matcher = GERRIT_CHANGE_REF_PATTERN.matcher(input);
			if (matcher.find()) {
				input = matcher.group(2);
			}
			return UIUtils.createProposalPattern(input);
		}, null, UIText.FetchGerritChangePage_ContentAssistTooltip);
