		} catch (IOException e) {
			exceptions
					.handleException(new CoreException(
							Activator
									.createErrorStatus(
											UIText.GitLightweightDecorator_AsynchronousDecorationError,
											e)));
			return;
