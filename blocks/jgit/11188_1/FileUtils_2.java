	public static boolean contentEquals(File f1
		BufferedInputStream s1 = null;
		BufferedInputStream s2 = null;
		try {
			s1 = new BufferedInputStream(new FileInputStream(f1));
			s2 = new BufferedInputStream(new FileInputStream(f2));
			int ch = s1.read();
			while (-1 != ch) {
				int ch2 = s2.read();
				if (ch != ch2) {
					return false;
				}
				ch = s1.read();
			}
			int ch2 = s2.read();
			return (ch2 == -1);
		} finally {
			if (s1 != null)
				s1.close();
			if (s2 != null)
				s2.close();
		}
	}

	public static void rename(final File src
			throws IOException {
		if (!src.renameTo(dst)) {
			if (src.isFile() && dst.isFile())
				if (src.length() == dst.length())
					if (contentEquals(src
						src.delete();
						return;
					}
			throw new IOException(MessageFormat.format(
					JGitText.get().renameFileFailed
					dst.getAbsolutePath()));
		}
	}

