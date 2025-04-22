		@Override
		public boolean next() throws IOException {
			while (nextWrapper()) {
				if(latestSkippedPrefix == null || !ref.getName().startsWith(latestSkippedPrefix)){
					return true;
				}
			}
			return false;
		}

		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			prefixName = prefixName + new String(LAST_UTF8_CHAR
			initRefIndex();

			byte[] key = prefixName.getBytes(UTF_8);

			block = seek(REF_BLOCK_TYPE
			latestSkippedPrefix = prefixName;
		}

