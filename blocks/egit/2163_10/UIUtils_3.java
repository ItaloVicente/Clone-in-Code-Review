	public static final void addRefContentProposalToText(final Text textField,
			final Repository repository, final IRefListProvider refListProvider) {
		KeyStroke stroke;
		try {
			stroke = KeyStroke.getInstance("M1+SPACE"); //$NON-NLS-1$
			UIUtils.addBulbDecorator(textField, NLS.bind(
					UIText.UIUtils_PressShortcutMessage, stroke.format()));
		} catch (ParseException e1) {
			Activator.handleError(e1.getMessage(), e1, false);
			stroke = null;
			UIUtils.addBulbDecorator(textField,
					UIText.UIUtils_StartTypingForPreviousValuesMessage);
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

				List<Ref> proposals = refListProvider.getRefList();

				if (proposals != null)
					for (final Ref ref : proposals) {
						final String shortenedName = Repository
								.shortenRefName(ref.getName());
						if (pattern != null
								&& !pattern.matcher(ref.getName()).matches()
								&& !pattern.matcher(shortenedName).matches())
							continue;

						IContentProposal propsal = new RefContentProposal(
								repository, ref);
						resultList.add(propsal);
					}

				return resultList.toArray(new IContentProposal[resultList
						.size()]);
			}
		};

		ContentProposalAdapter adapter = new ContentProposalAdapter(textField,
				new TextContentAdapter(), cp, stroke,
				UIUtils.VALUE_HELP_ACTIVATIONCHARS);
		adapter
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}
