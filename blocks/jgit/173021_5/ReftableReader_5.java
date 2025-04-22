		@Override
		public void seekPastPrefix(String prefixName) throws IOException {
			while (next()) {
				if (ref.getName().startsWith(prefixName)){
					break;
				}
			}
			BlockReader previousBlock = block;
			while (next()) {
				if (!ref.getName().startsWith(prefixName)){
					block = previousBlock;
					break;
				}
				previousBlock = block;
			}
		}

