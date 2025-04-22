		@Override
		public List<Ref> getRefsExcludingPrefixes(Set<String> prefixes) throws IOException {
			if (failing) {
				throw new IOException("disk failed
			}

			return super.getRefsExcludingPrefixes(prefixes);
		}

