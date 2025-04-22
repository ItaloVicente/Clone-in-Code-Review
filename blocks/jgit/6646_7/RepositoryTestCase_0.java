
	protected File writeTrashFiles(boolean ensureDistinctTimestamps
			String... contents)
			throws IOException
				File f = null;
				for (int i = 0; i < contents.length; i++)
					if (contents[i] != null) {
						if (ensureDistinctTimestamps && (f != null))
							fsTick(f);
						f = writeTrashFile(Integer.toString(i)
					}
				return f;
			}
