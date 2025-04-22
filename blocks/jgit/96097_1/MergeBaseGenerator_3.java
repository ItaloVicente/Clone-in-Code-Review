		return rc;
	}

	private static class CarryStack {
		final CarryStack prev;
		final RevCommit c;
		final int carry;

		CarryStack(CarryStack prev
			this.prev = prev;
			this.c = c;
			this.carry = carry;
		}
