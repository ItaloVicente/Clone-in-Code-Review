		if (this.index == index) {
			return;
		}
		if (this.index != WHOLE_COLLECTION
				|| index != 0
				|| isCollection()) {
			super.setIndex(index);
			value = UNINITIALIZED;
		}
	}
