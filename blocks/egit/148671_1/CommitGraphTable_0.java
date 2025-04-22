		renderer.paint(event, getHead());
	}

	private Ref getHead() {
		if (input == null) {
			return null;
		}
		if (head != null) {
			return head;
		}
		head = input.getHead();
		return head;
