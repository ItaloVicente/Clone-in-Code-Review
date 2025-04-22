	public PushCommand setPushAll() {
		refSpecs.add(Transport.REFSPEC_PUSH_ALL);
		return this;
	}

	public PushCommand setPushTags() {
		refSpecs.add(Transport.REFSPEC_TAGS);
		return this;
	}

	public PushCommand add(Ref ref) {
		refSpecs.add(new RefSpec(ref.getLeaf().getName()));
		return this;
	}

	public PushCommand add(String nameOrSpec) throws JGitInternalException {
		if (0 <= nameOrSpec.indexOf(':')) {
			refSpecs.add(new RefSpec(nameOrSpec));
		} else {
			Ref src;
			try {
				src = repo.getRef(nameOrSpec);
			} catch (IOException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfPushCommand
						e);
			}
			if (src != null)
				add(src);
		}
		return this;
	}

