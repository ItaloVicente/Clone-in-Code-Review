	static boolean isFileHeaderMagic(byte[] buf
		return (n - o) >= FILE_HEADER_MAGIC.length
				&& buf[o + 0] == FILE_HEADER_MAGIC[0]
				&& buf[o + 1] == FILE_HEADER_MAGIC[1]
				&& buf[o + 2] == FILE_HEADER_MAGIC[2]
				&& buf[o + 3] == FILE_HEADER_MAGIC[3];
	}

	static boolean isIndexMagic(byte[] buf
		return (n - o) >= INDEX_MAGIC.length
				&& buf[o + 0] == INDEX_MAGIC[0]
				&& buf[o + 1] == INDEX_MAGIC[1];
	}

