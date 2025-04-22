			this.type = type;
			this.aLine = aLine;
			this.bLine = bLine;
		}

		public @NonNull Type getType() {
			return type;
		}

		public int getLine(@NonNull DiffEntry.Side side) {
			if (DiffEntry.Side.NEW.equals(side)) {
				return bLine;
			}
			return aLine;
