			boolean exists = false;
			if (st.countTokens() == 2)
				exists = currentConfig.getString(st.nextToken(), null, st
						.nextToken()) != null;
			if (st.countTokens() == 3)
				exists = currentConfig.getString(st.nextToken(),
						st.nextToken(), st.nextToken()) != null;
			if (exists) {
				setErrorMessage(NLS.bind(
						UIText.AddConfigEntryDialog_EntryExistsMessage, keyText
								.getText()));
				hasError = true;
				return;
			}
