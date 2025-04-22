		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			prefixName = prefixName + LAST_UTF8_CHAR;
			initRefIndex();

			byte[] key = prefixName.getBytes(UTF_8);
			block = seek(REF_BLOCK_TYPE
		}

