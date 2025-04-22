			if (amend && headId != null && !noPostRewrite) {
				final ObjectId oldId = headId;
				final ObjectId newId = revCommit.getId();
				final String rewritten = oldId.getName() + ' '
						+ newId.getName() + '\n';
				int postRewriteHookResult = FS.DETECTED.runIfPresent(repo
						Hook.POST_REWRITE
						System.out
				if (postRewriteHookResult != 0) {
				}
			}
