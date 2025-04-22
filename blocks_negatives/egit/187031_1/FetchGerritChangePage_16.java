		for (String aUri : uris) {
			uriCombo.add(aUri);
			changeRefs.put(aUri, new ChangeList(repository, aUri));
		}
		if (defaultUri != null) {
			uriCombo.setText(defaultUri);
		} else {
			selectLastUsedUri();
		}
		String currentUri = uriCombo.getText();
		ChangeList list = changeRefs.get(currentUri);
		if (list == null) {
			list = new ChangeList(repository, currentUri);
			changeRefs.put(currentUri, list);
		}
		preFetch(list);
		refText.setFocus();
		Dialog.applyDialogFont(main);
		setControl(main);
		if (candidateChange != null) {
			final IWizardContainer container = getContainer();
			if (container instanceof IPageChangeProvider) {
				((IPageChangeProvider) container)
						.addPageChangedListener(new IPageChangedListener() {
							@Override
							public void pageChanged(PageChangedEvent event) {
								if (event
										.getSelectedPage() == FetchGerritChangePage.this) {
									event.getPageChangeProvider()
											.removePageChangedListener(this);
									getControl().getDisplay()
											.asyncExec(new Runnable() {
										@Override
										public void run() {
											Control control = getControl();
											if (control != null
													&& !control.isDisposed()) {
												contentProposer
														.openProposalPopup();
											}
										}
									});
								}
							}
						});
			}
		}
		checkPage();
