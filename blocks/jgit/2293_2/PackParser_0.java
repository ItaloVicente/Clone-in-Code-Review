
					if (entryCount < objectCount) {
						throw new IOException(MessageFormat.format(JGitText
								.get().packHasUnresolvedDeltas
								(objectCount - entryCount)));
					}
