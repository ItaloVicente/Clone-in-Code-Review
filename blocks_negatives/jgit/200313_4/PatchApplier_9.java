				String filterCommand = walk != null
						? walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_CLEAN)
						: null;
				RawText raw = getRawText(f, fileStreamSupplier, fileId,
						pathWithOriginalContent, loadedFromTreeWalk, filterCommand,
						convertCrLf);
				resultStreamLoader = applyText(raw, fh);
