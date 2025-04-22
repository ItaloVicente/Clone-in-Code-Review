				switch (state) {
				case REBASING_INTERACTIVE:
					return true;
				case REBASING_REBASING:
					return true;
				case REBASING_MERGE:
					return true;
				default:
					return false;
				}

				switch (state) {
				case REBASING_INTERACTIVE:
					return true;
				case REBASING_MERGE:
					return true;
				default:
					return false;
				}

