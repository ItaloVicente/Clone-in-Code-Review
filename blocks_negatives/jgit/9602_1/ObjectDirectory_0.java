			throw new IOException(MessageFormat.format(JGitText.get().notAValidPack, idx));

		if (!p.substring(0, 45).equals(i.substring(0, 45)))
			throw new IOException(MessageFormat.format(JGitText.get().packDoesNotMatchIndex, pack));

		PackFile res = new PackFile(idx, pack);
