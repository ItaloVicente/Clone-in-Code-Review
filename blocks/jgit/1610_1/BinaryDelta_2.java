		if (result == null)
			result = new byte[resLen];
		else if (result.length != resLen)
			throw new IllegalArgumentException(
					JGitText.get().resultLengthIncorrect);

