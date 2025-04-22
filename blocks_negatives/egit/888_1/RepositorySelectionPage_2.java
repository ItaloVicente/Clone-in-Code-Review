	private void addContentProposalToUriText(Text uriTextField) {

		UIUtils.addBulbDecorator(uriTextField, UIText.RepositorySelectionPage_ShowPreviousURIs_HoverText);

		IContentProposalProvider cp = new IContentProposalProvider() {

			public IContentProposal[] getProposals(String contents, int position) {

				List<IContentProposal> resultList = new ArrayList<IContentProposal>();

				String patternString = contents;
				while (patternString.length() > 0
						&& patternString.charAt(0) == ' ')
					patternString = patternString.substring(1);
				patternString = patternString.replaceAll("\\x2A", ".*"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				Pattern pattern;
				try {
					pattern = Pattern.compile(patternString,
							Pattern.CASE_INSENSITIVE);
				} catch (PatternSyntaxException e) {
					pattern = null;
				}

				List<String> uriStrings = getUrisFromPrefs();
				for (final String uriString : uriStrings) {

					if (pattern != null
							&& !pattern.matcher(uriString).matches())
						continue;

					IContentProposal propsal = new IContentProposal() {

						public String getLabel() {
							return null;
						}

						public String getDescription() {
							return null;
						}

						public int getCursorPosition() {
							return 0;
						}

						public String getContent() {
							return uriString;
						}
					};
					resultList.add(propsal);
				}

				return resultList.toArray(new IContentProposal[resultList
						.size()]);
			}
		};

		new ContentProposalAdapter(uriTextField, new TextContentAdapter(), cp,
				null, null)
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}

