
		if (s.startsWith("^+") || s.startsWith("+^")) {
			throw new IllegalArgumentException(
					JGitText.get().invalidNegativeAndForce);
		}

