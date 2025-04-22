
		public boolean updateSucceeded() {
			switch (this) {
			case NO_CHANGE:
			case NEW:
			case FORCED:
			case FAST_FORWARD:
			case RENAMED:
				return true;
			default:
				return false;
			}
		}

