				} catch (InterruptedException e) {
					final String message = NLS
							.bind(CoreText.RecursiveModelMerger_ScopeInitializationInterrupted,
									pathString);
					Activator.logError(message, e);
					cleanUp();
					return false;
