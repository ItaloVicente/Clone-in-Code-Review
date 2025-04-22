		} else if (version != DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
				|| previous == null || toRemove == previous.path.length) {
			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			byte[] buf = new byte[NAME_MASK];
			IO.readFully(in
			tmp.write(buf);
			readNulTerminatedString(in
