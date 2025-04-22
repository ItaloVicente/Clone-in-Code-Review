		KeyStroke stroke = UIUtils
				.getKeystrokeOfBestActiveBindingFor(IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST);
		if (stroke != null) {
			UIUtils.addBulbDecorator(textField, NLS.bind(
					UIText.FetchGerritChangePage_ContentAssistTooltip,
					stroke.format()));
		}
		IContentProposalProvider cp = new IContentProposalProvider() {

			@Override
			public IContentProposal[] getProposals(String contents, int position) {
				Collection<Change> proposals;
				try {
					proposals = getRefsForContentAssist(contents);
				} catch (InvocationTargetException e) {
					Activator.handleError(e.getMessage(), e, true);
					return null;
				} catch (InterruptedException e) {
					return null;
				}

				if (proposals == null) {
					return null;
				}
				List<IContentProposal> resultList = new ArrayList<>();
				String input = contents;
				Matcher matcher = GERRIT_CHANGE_REF_PATTERN.matcher(contents);
				if (matcher.find()) {
					input = matcher.group(2);
				}
				Pattern pattern = UIUtils.createProposalPattern(input);
				for (final Change ref : proposals) {
					if (pattern != null && !pattern
							.matcher(ref.getChangeNumber().toString())
							.matches()) {
						continue;
					}
					resultList.add(new ChangeContentProposal(ref));
				}
				return resultList
						.toArray(new IContentProposal[resultList.size()]);
