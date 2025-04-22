			} catch (JGitInternalException
					| UnsupportedSigningFormatException e) {
				if (config.isSignCommits()) {
					if (e instanceof JGitInternalException) {
						throw (JGitInternalException) e;
					} else {
						throw new JGitInternalException(e.getMessage(), e);
					}
