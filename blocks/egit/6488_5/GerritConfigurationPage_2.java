	private void addRefContentProposalToText(final Text textField) {
		KeyStroke stroke;
		try {
			stroke = KeyStroke.getInstance("CTRL+SPACE"); //$NON-NLS-1$
			UIUtils.addBulbDecorator(textField, NLS.bind(
					UIText.GerritConfigurationPage_BranchTooltipHover,
					stroke.format()));
		} catch (ParseException e1) {
			Activator.handleError(e1.getMessage(), e1, false);
			stroke = null;
		}

		IContentProposalProvider cp = new IContentProposalProvider() {
			public IContentProposal[] getProposals(String contents, int position) {
				List<IContentProposal> resultList = new ArrayList<IContentProposal>();

				String patternString = contents;
				while (patternString.length() > 0
						&& patternString.charAt(0) == ' ') {
					patternString = patternString.substring(1);
				}

				patternString = Pattern.quote(patternString);

				patternString = patternString.replaceAll("\\x2A", ".*"); //$NON-NLS-1$ //$NON-NLS-2$

				if (!patternString.endsWith(".*")) //$NON-NLS-1$
					patternString = patternString + ".*"; //$NON-NLS-1$

				Pattern pattern;
				try {
					pattern = Pattern.compile(patternString,
							Pattern.CASE_INSENSITIVE);
				} catch (PatternSyntaxException e) {
					pattern = null;
				}

				Set<String> proposals = new TreeSet<String>();

				try {
					Set<String> remotes = repository
							.getRefDatabase()
							.getRefs(Constants.R_REMOTES + remoteName + "/").keySet(); //$NON-NLS-1$
					proposals.addAll(remotes);
				} catch (IOException e) {
				}

				for (final String proposal : proposals) {
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

		public String getContent() {
			return myString;
		}

		public int getCursorPosition() {
			return 0;
		}

		public String getDescription() {
			return myString;
		}

		public String getLabel() {
			return myString;
		}

		@Override
		public String toString() {
			return getContent();
		}
	}

