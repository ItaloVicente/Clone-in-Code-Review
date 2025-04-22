		UIUtils.addRefContentProposalToText(remoteBranchNameText, this.repository, new IRefListProvider() {
			public List<Ref> getRefList() {
				if (PushBranchPage.this.assist != null) {
					return PushBranchPage.this.assist.getRefsForContentAssist(false, true);
				}
				return Collections.emptyList();
			}
		});
