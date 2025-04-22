		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			initRefIndex();
			byte[] key = prefixName.getBytes(UTF_8);
			ByteBuffer byteBuffer = ByteBuffer.allocate(key.length + 1);
			byteBuffer.put(key);
			byteBuffer.put(LAST_LEXICOGRAPHICAL_BYTE);

			block = seek(REF_BLOCK_TYPE
		}

