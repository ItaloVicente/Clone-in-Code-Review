		UIUtils.addRefContentProposalToText(remoteBranchNameText,
				this.repository, () -> {
					if (PullWizardPage.this.assist != null) {
						return PullWizardPage.this.assist
								.getRefsForContentAssist(false, true);
					}
					return Collections.emptyList();
				}, true);
