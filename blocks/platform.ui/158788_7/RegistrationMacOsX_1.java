		String path = Stream.of(lsRegisterEntries). //
				parallel().//
				filter(s -> s.startsWith(keyOfFirstLine)).//
				filter(s -> s.contains(scheme + ":")).// //$NON-NLS-1$
				map(s -> pattern.matcher(s)).//
				filter(m -> m.find()).//
				map(m -> m.group(1)).findFirst().map(s -> s.replaceFirst(TRAILING_HEX_VALUE_WITH_BRACKETS, "")) //$NON-NLS-1$
				.orElse(""); //$NON-NLS-1$

		return path;
