		if (baseSize != baseStream.size()) {
			deltaStream.close();
			baseStream.close();
			throw new CorruptObjectException(JGitText.get().baseLengthIncorrect);
		}

