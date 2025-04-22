	private void addIncludedConfig(final List<ConfigLine> newEntries
			ConfigLine line
				line.value == null || line.value.equals(MAGIC_EMPTY_VALUE)) {
			throw new ConfigInvalidException(
					JGitText.get().invalidLineInConfigFile);
		}
		byte[] bytes = readIncludedConfig(line.value);
		if (bytes == null) {
			return;
		}

		String decoded;
		if (isUtf8(bytes)) {
			decoded = RawParseUtils.decode(RawParseUtils.UTF8_CHARSET
					bytes.length);
		} else {
			decoded = RawParseUtils.decode(bytes);
		}
		newEntries.addAll(fromTextRecurse(decoded
	}

