					if (PatchType.GIT_BINARY.equals(fh.getPatchType())) {
						applyBinary(repository
								file::openEntryStream
								checkOut);
					} else {
						command = walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_CLEAN);
						RawText raw;
						try (InputStream input = filterClean(repository
								new FileInputStream(f)
							raw = new RawText(
									IO.readWholeStream(input
						}
						applyText(repository
