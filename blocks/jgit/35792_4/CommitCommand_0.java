
			if (amend && headId != null && !noPostRewrite) {
				final ObjectId oldId = headId;
				final ObjectId newId = revCommit.getId();
				final String rewritten = oldId.getName() + ' '
						+ newId.getName() + '\n';
				ProcessResult postRewriteHookResult = FS.DETECTED.runIfPresent(
						repo
						System.out
				if (postRewriteHookResult.getStatus() == ProcessResult.Status.OK
						&& postRewriteHookResult.getExitCode() != 0) {
				}
			}
			return revCommit;
