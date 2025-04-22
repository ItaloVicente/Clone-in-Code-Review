
	public void saveUriInPrefs(String stringToAdd) {

		Set<String> uriStrings = getUrisFromPrefs();

		if (uriStrings.add(stringToAdd)) {

			IEclipsePreferences prefs = new InstanceScope().getNode(Activator
					.getPluginId());

			StringBuilder sb = new StringBuilder();
			StringBuilder lb = new StringBuilder();

			for (String uriString : uriStrings) {
				sb.append(uriString);
				lb.append(uriString.length());
				lb.append(" "); //$NON-NLS-1$
			}
			prefs.put(USED_URIS_PREF, sb.toString());
			prefs.put(USED_URIS_LENGTH_PREF, lb.toString());

			try {
				prefs.flush();
			} catch (BackingStoreException e) {
			}
		}
	}

	public Set<String> getUrisFromPrefs() {

		Set<String> uriStrings = new TreeSet<String>();

		IEclipsePreferences prefs = new InstanceScope().getNode(Activator
				.getPluginId());
		String uriLengths = prefs.get(USED_URIS_LENGTH_PREF, ""); //$NON-NLS-1$
		String uris = prefs.get(USED_URIS_PREF, ""); //$NON-NLS-1$

		StringTokenizer tok = new StringTokenizer(uriLengths, " "); //$NON-NLS-1$
		int offset = 0;
		while (tok.hasMoreTokens()) {
			try {
				int length = Integer.parseInt(tok.nextToken());
				if (uris.length() >= (offset + length)) {
					uriStrings.add(uris.substring(offset, offset + length));
					offset += length;
				}
			} catch (NumberFormatException nfe) {
			}

		}

		return uriStrings;
	}

	private void addContentProposalToUriText(Text uriTextField) {

		KeyStroke keyStroke;

		try {
			keyStroke = KeyStroke.getInstance("M1+Space"); //$NON-NLS-1$
		} catch (ParseException e) {
			return;
		}
		ControlDecoration dec = new ControlDecoration(uriTextField, SWT.TOP
				| SWT.LEFT);

		if (Platform.isRunning()) {
			dec.setImage(FieldDecorationRegistry.getDefault()
					.getFieldDecoration(
							FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
					.getImage());
		}
		dec.setShowOnlyOnFocus(true);
		dec.setShowHover(true);

		dec.setDescriptionText(NLS.bind(
				UIText.RepositorySelectionPage_ShowPrevisousURIs_Tooltip,
				keyStroke.format()));

		IContentProposalProvider cp = new IContentProposalProvider() {

			public IContentProposal[] getProposals(String contents, int position) {
				List<IContentProposal> resultList = new ArrayList<IContentProposal>();

				Set<String> uriStrings = getUrisFromPrefs();
				for (final String uriString : uriStrings) {
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
				keyStroke, new char[0])
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

	}
