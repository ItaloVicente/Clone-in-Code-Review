		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return shallowCommits;
		}

		@Override
		public void setShallowCommits(Set<ObjectId> shallowCommits) {
			this.shallowCommits = shallowCommits;
		}

