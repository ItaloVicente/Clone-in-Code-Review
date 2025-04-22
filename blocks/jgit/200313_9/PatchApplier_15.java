		if (fileStreamSupplier == null)
			fileStreamSupplier = inCore() ? InputStream::nullInputStream
					: () -> new FileInputStream(f);

		FileMode fileMode = fh.getNewMode() != null ? fh.getNewMode()
				: FileMode.REGULAR_FILE;
		ContentStreamLoader resultStreamLoader;
		if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
			resultStreamLoader = applyBinary(pathWithOriginalContent
					fileStreamSupplier
		} else {
			String filterCommand = walk != null
					? walk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_CLEAN)
					: null;
			RawText raw = getRawText(f
					pathWithOriginalContent
					convertCrLf);
			resultStreamLoader = applyText(raw
		}
		if (resultStreamLoader == null || !result.getErrors().isEmpty()) {
			return;
		}
