			} catch (JGitInternalException e) {
				String errorMessage = e.getCause() != null ? e.getCause()
						.getMessage() : e.getMessage();
				String userMessage = NLS.bind(
						CoreText.PushOperation_InternalExceptionOccurredMessage,
						errorMessage);
				URIish uri = getPushURIForErrorHandling();
				handleException(uri, e, userMessage);
			} catch (Exception e) {
				URIish uri = getPushURIForErrorHandling();
				handleException(uri, e, e.getMessage());
