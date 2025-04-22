				try {
					pushUpstream(commit, pushMode);
				} catch (IOException e) {
					return Activator.createErrorStatus(
							UIText.PushJob_unexpectedError, e);
				}
