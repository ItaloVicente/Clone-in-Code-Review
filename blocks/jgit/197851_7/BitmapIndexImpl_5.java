		@Override
		public String toString() {
			StringBuilder out = new StringBuilder();
			IntIterator iter = bitmap.intIterator();
			while(iter.hasNext()) {
				int pos = iter.next();
				out.append(String.format(" => [%s] %s\n"
			}
			return out.toString();
		}

