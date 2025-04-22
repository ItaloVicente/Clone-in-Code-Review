
		public boolean updateSucceeded() {
			switch (this) {
			case NEW:
			case FORCED:
			case FAST_FORWARD:
			case RENAMED:
				return true;
			default:
				return false;
			}
		}

