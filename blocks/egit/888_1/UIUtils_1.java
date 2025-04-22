	public static IPreviousValueProposalHandler addPreviousValuesContentProposalToText(
			final Text textField, final String preferenceKey) {
		KeyStroke stroke;
		try {
			stroke = KeyStroke.getInstance("M1+SPACE"); //$NON-NLS-1$
			addBulbDecorator(textField, NLS.bind(
					UIText.UIUtils_PressShortcutMessage, stroke.format()));
		} catch (ParseException e1) {
			Activator.handleError(e1.getMessage(), e1, false);
			stroke = null;
			addBulbDecorator(textField,
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

		ContentProposalAdapter adapter = new ContentProposalAdapter(textField,
				new TextContentAdapter(), cp, stroke,
				VALUE_HELP_ACTIVATIONCHARS);
		adapter
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

		return new IPreviousValueProposalHandler() {
			public void updateProposals() {
				String value = textField.getText();
				if (value.length() > 0) {
					if (value.length() > 2000) {
						value = value.substring(0, 1999);
					}
					IDialogSettings settings = org.eclipse.egit.ui.Activator
							.getDefault().getDialogSettings();
					String[] existingValues = settings.getArray(preferenceKey);
					if (existingValues == null) {
						existingValues = new String[] { value };
						settings.put(preferenceKey, existingValues);
					} else {

						List<String> values = new ArrayList<String>(
								existingValues.length + 1);

						for (String existingValue : existingValues)
							values.add(existingValue);
						if (values.indexOf(value) == 0)
							return;

						values.remove(value);
						values.add(0, value);
						while (values.size() > 10)
							values.remove(values.size() - 1);

						settings.put(preferenceKey, values
								.toArray(new String[values.size()]));
					}
				}
			}
		};
	}

