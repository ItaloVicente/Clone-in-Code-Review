
			if (!operationResult.isSuccessfulConnectionForAnyURI()) {
				String errorMessage = NLS.bind(CoreText.pushToRemoteFailed,
						operationResult.getErrorStringForAllURis());
				throw new CoreException(error(errorMessage));
			}
