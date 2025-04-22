		UIUtils.<String> addContentProposalToText(textField,
				() -> {
					try {
						Set<String> sortedSet = new TreeSet<>(
								String.CASE_INSENSITIVE_ORDER);
						sortedSet.addAll(repository.getRefDatabase()
								.getRefs(Constants.R_REMOTES + remoteName + '/')
								.keySet());
						return sortedSet;
					} catch (IOException e) {
						return Collections.emptyList();
					}
				}, (pattern, refName) -> {
					if (pattern != null
							&& !pattern.matcher(refName).matches()) {
						return null;
					}
					return new ContentProposal(refName);
				}, UIText.GerritConfigurationPage_BranchTooltipStartTyping,
				UIText.GerritConfigurationPage_BranchTooltipHover);
