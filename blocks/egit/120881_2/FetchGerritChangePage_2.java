		checkoutFetchHead.addSelectionListener(validatePage);

		try {
			String headName = repository.getBranch();
			if (headName != null) {
				cherryPickFetchHead = new Button(checkoutGroup, SWT.RADIO);
				GridDataFactory.fillDefaults().span(3, 1)
						.applyTo(cherryPickFetchHead);
				cherryPickFetchHead.setText(MessageFormat.format(
						UIText.FetchGerritChangePage_CherryPickRadio,
						headName));
				cherryPickFetchHead.addSelectionListener(validatePage);
