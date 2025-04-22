		} else {
			Label noResult = new Label(fetchResultGroup, SWT.NONE);
			if (result.getFetchedFrom().equals(".")) //$NON-NLS-1$
				noResult
						.setText(UIText.PullResultDialog_NothingToFetchFromLocal);
			else
				noResult.setText(NLS.bind(
						UIText.FetchResultDialog_labelEmptyResult, result
								.getFetchedFrom()));

