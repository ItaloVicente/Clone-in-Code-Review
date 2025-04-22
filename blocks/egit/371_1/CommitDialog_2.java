
	private void addContentProposalToText(Text textField,
			final String preferenceKey) {

		ControlDecoration dec = new ControlDecoration(textField, SWT.TOP
				| SWT.LEFT);

		if (Platform.isRunning()) {
			dec.setImage(FieldDecorationRegistry.getDefault()
					.getFieldDecoration(
							FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
					.getImage());
		}
		dec.setShowOnlyOnFocus(true);
		dec.setShowHover(true);

		dec
				.setDescriptionText(UIText.CommitDialog_ValueHelp_Message);

		IContentProposalProvider cp = new IContentProposalProvider() {

			public IContentProposal[] getProposals(String contents, int position) {
				List<IContentProposal> resultList = new ArrayList<IContentProposal>();

				String patternString = contents.replaceAll("\\x2A", ".*"); //$NON-NLS-1$ //$NON-NLS-2$
				if (!patternString.endsWith(".*")) { //$NON-NLS-1$
					patternString = patternString + ".*"; //$NON-NLS-1$
				}
				Pattern pattern;
				try {
					pattern = Pattern.compile(patternString,
							Pattern.CASE_INSENSITIVE);
				} catch (PatternSyntaxException e) {
					pattern = null;
				}

				String[] proposals = org.eclipse.egit.ui.Activator.getDefault()
						.getDialogSettings().getArray(preferenceKey);
				if (proposals != null)
					for (final String uriString : proposals) {

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

		new ContentProposalAdapter(textField, new TextContentAdapter(), cp,
				null, null)
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}
