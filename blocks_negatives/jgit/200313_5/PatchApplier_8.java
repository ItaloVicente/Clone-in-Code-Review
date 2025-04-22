			} else if (walk.next()) {
				streamType = convertCrLf ? EolStreamType.TEXT_CRLF
						: walk.getEolStreamType(OperationType.CHECKOUT_OP);
				smudgeFilterCommand = walk
						.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
				FileTreeIterator file = walk.getTree(FILE_TREE_INDEX,
						FileTreeIterator.class);
				if (file != null) {
					fileId = file.getEntryObjectId();
					fileStreamSupplier = file::openEntryStream;
					loadedFromTreeWalk = true;
				} else {
					throw new PatchApplyException(MessageFormat.format(
							JGitText.get().cannotReadFile,
							pathWithOriginalContent));
				}
