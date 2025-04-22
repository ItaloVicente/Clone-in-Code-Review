		UIUtils.<String> addContentProposalToText(textField,
				() -> knownRemoteRefs, (pattern, refName) -> {
					if (pattern != null
							&& !pattern.matcher(refName).matches()) {
						return null;
					}
					return new ContentProposal(refName);
				}, UIText.PushToGerritPage_ContentProposalStartTypingText,
				UIText.PushToGerritPage_ContentProposalHoverText);
