				InputStream filtered = Filtering.filter(repository, gitPath,
						new ByteArrayInputStream(newContent), command);
				if (streamType == null) {
					WorkingTreeOptions workingTreeOptions = repository
							.getConfig().get(WorkingTreeOptions.KEY);
					if (workingTreeOptions.getAutoCRLF() == AutoCRLF.FALSE) {
						streamType = EolStreamType.DIRECT;
					} else {
						streamType = EolStreamType.AUTO_LF;
					}
				}
				ByteBuffer content = IO.readWholeStream(
						EolStreamTypeUtil.wrapInputStream(filtered, streamType),
						newContent.length);
				editor.add(
						new DirCacheEntryEditor(gitPath, repository, content));
