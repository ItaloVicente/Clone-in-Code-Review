	public final void retainOnReset(RevFlag flag) {
		if ((freeFlags & flag.mask) != 0)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagIsDisposed
		if (flag.walker != this)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().flagNotFromThis
		retainOnReset |= flag.mask;
	}

	public final void retainOnReset(Collection<RevFlag> flags) {
		for (RevFlag f : flags)
			retainOnReset(f);
	}

