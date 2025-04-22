		return newEntries;
	}

	private void addIncludedConfig(final List<ConfigLine> newEntries
			ConfigLine line
				line.value == null || line.value.equals(MAGIC_EMPTY_VALUE)) {
			throw new ConfigInvalidException(
					JGitText.get().invalidLineInConfigFile);
		}
		File path = new File(line.value);
		try {
			byte[] bytes = IO.readFully(path);
			String decoded;
			if (isUtf8(bytes)) {
				decoded = RawParseUtils.decode(RawParseUtils.UTF8_CHARSET
						bytes
			} else {
				decoded = RawParseUtils.decode(bytes);
			}
			newEntries.addAll(fromTextRecurse(decoded
		} catch (IOException ioe) {
			throw new ConfigInvalidException(MessageFormat.format(
					JGitText.get().invalidIncludedPathInConfigFile
		}
