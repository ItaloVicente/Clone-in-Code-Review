			FileMode fileMode = fh.getNewMode() != null ? fh.getNewMode()
					: FileMode.REGULAR_FILE;
			ContentStreamLoader resultStreamLoader;
			if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
				resultStreamLoader = applyBinary(pathWithOriginalContent, f, fh,
						fileStreamSupplier, fileId);
			} else {
				String filterCommand = walk != null
						? walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_CLEAN)
						: null;
				RawText raw = getRawText(f, fileStreamSupplier, fileId,
						pathWithOriginalContent, loadedFromTreeWalk, filterCommand,
						convertCrLf);
				resultStreamLoader = applyText(raw, fh);
			}

			if (f != null) {
				TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
				try {
					CheckoutMetadata metadata = new CheckoutMetadata(streamType,
							smudgeFilterCommand);

					try (TemporaryBuffer buf = buffer) {
						DirCacheCheckout.getContent(repo, pathWithOriginalContent,
								metadata, resultStreamLoader.supplier, workingTreeOptions,
								buf);
					}
					try (InputStream bufIn = buffer.openInputStream()) {
						Files.copy(bufIn, f.toPath(),
								StandardCopyOption.REPLACE_EXISTING);
					}
				} finally {
					buffer.destroy();
