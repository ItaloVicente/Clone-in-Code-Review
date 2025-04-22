			this.repository = repository;
			this.uriText = uriText;
		}

		@Override
		protected String getJobTitle() {
			return MessageFormat.format(
					UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
					uriText);
		}

		@Override
		protected void prepareRun() throws InvocationTargetException {
			try {
				listOp = new ListRemoteOperation(repository,
						new URIish(uriText),
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));
			} catch (URISyntaxException e) {
				throw new InvocationTargetException(e);
			}
