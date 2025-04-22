			spec = new PushOperationSpecification();
			for (final URIish uri : pushURIs)
				spec.addURIRefUpdates(uri,
						ConfirmationPage.copyUpdates(updates));
			int timeout = Activator.getDefault().getPreferenceStore().getInt(
					UIPreferences.REMOTE_CONNECTION_TIMEOUT);
			op = new PushOperation(repository, spec, dryRun, config, timeout);

		} catch (URISyntaxException e) {
			pushException = e;
			return;
		} catch (IOException e) {
			pushException = e;
			return;
		} finally {
			if (pushException != null)
				Activator.handleError(pushException.getMessage(),
						pushException, shell != null);
		}
