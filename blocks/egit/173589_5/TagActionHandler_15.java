		TagOperation operation = new TagOperation(repo)
				.setName(tagName)
				.setTarget(tagTarget)
				.setAnnotated(dialog.isAnnotated())
				.setForce(dialog.shouldOverWriteTag())
				.setSign(dialog.shouldSign())
				.setMessage(dialog.getTagMessage())
				.setCredentialsProvider(new EGitCredentialsProvider());
