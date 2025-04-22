			} catch (JGitInternalException e) {
				String errorMessage = e.getCause() != null ? e.getCause()
						.getMessage() : e.getMessage();
				String userMessage = NLS.bind(
						CoreText.PushOperation_InternalExceptionOccuredMessage,
						errorMessage);
				URIish uri = rc.getPushURIs().isEmpty() ? rc.getURIs().get(0)
						: rc.getPushURIs().get(0);
				operationResult.addOperationResult(uri, userMessage);
			} catch (InvalidRemoteException e) {
				URIish uri = rc.getPushURIs().isEmpty() ? rc.getURIs().get(0)
						: rc.getPushURIs().get(0);
				operationResult.addOperationResult(uri, e.getMessage());
