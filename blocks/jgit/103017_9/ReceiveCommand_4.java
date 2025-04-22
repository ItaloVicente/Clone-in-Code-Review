			throw new IllegalArgumentException(
					JGitText.get().newIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
