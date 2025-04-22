
					monitor.update(1);
					if (monitor.isCancelled()) {
						throw new CanceledException(MessageFormat.format(
								JGitText.get().operationCanceled
								JGitText.get().checkingOutFiles));
					}
