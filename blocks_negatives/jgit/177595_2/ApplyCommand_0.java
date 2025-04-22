					command = walk
							.getFilterCommand(Constants.ATTR_FILTER_TYPE_CLEAN);
					RawText raw;
					try (InputStream input = filterClean(repository, path,
							new FileInputStream(f), convertCrLf, command)) {
						raw = new RawText(IO.readWholeStream(input, 0).array());
