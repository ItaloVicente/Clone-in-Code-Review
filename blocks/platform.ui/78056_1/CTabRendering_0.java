	static enum CirclePart {
		LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM;

		static CirclePart left(boolean onBottom) {
			if (onBottom) {
				return LEFT_BOTTOM;
			}
			return LEFT_TOP;
		}

		static CirclePart right(boolean onBottom) {
			if (onBottom) {
				return RIGHT_BOTTOM;
			}
			return RIGHT_TOP;
		}

		boolean isBottom() {
			return this == LEFT_BOTTOM || this == RIGHT_BOTTOM;
		}
	}
