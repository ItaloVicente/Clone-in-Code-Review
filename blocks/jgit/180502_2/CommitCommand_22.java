			try {
				Hooks.postCommit(repo
						hookErrRedirect.get(PostCommitHook.NAME)).call();
			} catch (Exception e) {
				log.error(MessageFormat.format(
						JGitText.get().postCommitHookFailed
						e);
			}
			return revCommit;
