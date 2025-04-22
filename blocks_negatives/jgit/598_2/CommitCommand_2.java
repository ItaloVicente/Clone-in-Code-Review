					throw new JGitInternalException(
							"Updating the ref "
									+ Constants.HEAD
									+ " to "
									+ commitId.toString()
									+ " failed. ReturnCode from RefUpdate.update() was "
									+ rc);
