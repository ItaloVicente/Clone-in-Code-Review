			config = new RemoteConfig(repository.getConfig(), remoteName);
			if (config.getURIs().isEmpty()) {
				throw new IOException(
						NLS.bind(
								UIText.FetchConfiguredRemoteAction_NoUrisDefinedMessage,
								remoteName));
			}
			if (config.getFetchRefSpecs().isEmpty()) {
				throw new IOException(
						NLS.bind(
								UIText.FetchConfiguredRemoteAction_NoSpecsDefinedMessage,
								remoteName));
			}
			transport = Transport.open(repository, config);
		} catch (URISyntaxException e) {
			prepareException = e;
			return;
		} catch (IOException e) {
			prepareException = e;
			return;
		} finally {
			if (prepareException != null)
				Activator.handleError(prepareException.getMessage(),
						prepareException, shell != null);
