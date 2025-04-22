			try {
				setter.addValue(new FileTreeIterator(new File(name)
						FS.DETECTED));
			} catch (IOException e) {
				throw new CmdLineException(e.getMessage());
			}
