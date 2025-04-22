	public PullCommand add(Ref ref) {
		refSpecs.add(new RefSpec(ref.getLeaf().getName()));
		return this;
	}

	public PullCommand add(String nameOrSpec) throws JGitInternalException {
		if (nameOrSpec.indexOf(':') != -1)
			refSpecs.add(new RefSpec(nameOrSpec));
		else {
			Ref src;
			try {
				src = repo.getRef(nameOrSpec);
			} catch (IOException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfPullCommand
						e);
			}
			if (src != null)
				add(src);
		}
		return this;
	}

	public PullCommand setRefSpecs(RefSpec... specs) {
		checkCallable();
		refSpecs.clear();
		Collections.addAll(refSpecs
		return this;
	}

	public PullCommand setRefSpecs(List<RefSpec> specs) {
		checkCallable();
		refSpecs.clear();
		refSpecs.addAll(specs);
		return this;
	}
