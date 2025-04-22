	public static class FSFactory {
		protected FSFactory() {
		}

		public FS detect(Boolean cygwinUsed) {
			if (SystemReader.getInstance().isWindows()) {
				if (cygwinUsed == null)
					cygwinUsed = Boolean.valueOf(FS_Win32_Cygwin.isCygwin());
				if (cygwinUsed.booleanValue())
					return new FS_Win32_Cygwin();
				else
					return new FS_Win32();
			} else if (FS_POSIX_Java6.hasExecute())
				return new FS_POSIX_Java6();
			else
				return new FS_POSIX_Java5();
		}
	}

