		if (knownRemoteRefs.isEmpty()) {
			return;
		}
		KeyStroke stroke = UIUtils
				.getKeystrokeOfBestActiveBindingFor(IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST);
		if (stroke != null)
			UIUtils.addBulbDecorator(textField, NLS.bind(
					UIText.PushToGerritPage_ContentProposalHoverText,
					stroke.format()));

		IContentProposalProvider cp = new IContentProposalProvider() {
			@Override
			public IContentProposal[] getProposals(String contents, int position) {
				List<IContentProposal> resultList = new ArrayList<>();

				String patternString = contents;
				while (patternString.length() > 0
						&& patternString.charAt(0) == ' ') {
					patternString = patternString.substring(1);
				}

				patternString = Pattern.quote(patternString);

				patternString = patternString.replaceAll("\\x2A", ".*"); //$NON-NLS-1$ //$NON-NLS-2$


				Pattern pattern;
				try {
					pattern = Pattern.compile(patternString,
							Pattern.CASE_INSENSITIVE);
				} catch (PatternSyntaxException e) {
					pattern = null;
				}

				for (final String proposal : knownRemoteRefs) {
					if (pattern != null && !pattern.matcher(proposal).matches())
						continue;
					IContentProposal propsal = new BranchContentProposal(
							proposal);
					resultList.add(propsal);
				}

				return resultList.toArray(new IContentProposal[resultList
						.size()]);
			}
		};

		ContentProposalAdapter adapter = new ContentProposalAdapter(textField,
				new TextContentAdapter(), cp, stroke, null);
		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	private final static class BranchContentProposal implements
			IContentProposal {
		private final String myString;

		BranchContentProposal(String string) {
			myString = string;
		}

		@Override
		public String getContent() {
			return myString;
		}

		@Override
		public int getCursorPosition() {
			return 0;
		}

		@Override
		public String getDescription() {
			return myString;
		}

		@Override
		public String getLabel() {
			return myString;
		}

		@Override
		public String toString() {
			return getContent();
		}
