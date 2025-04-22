		int getDepth() {
			return depth;
		}

		boolean hasDeepenNots() {
			return !deepenNots.isEmpty();
		}

		Builder addDeepenNot(String deepenNot) {
			deepenNots.add(deepenNot);
			return this;
		}

		Builder setDeepenSince(int value) {
			deepenSince = value;
			return this;
		}

		int getDeepenSince() {
			return deepenSince;
		}

