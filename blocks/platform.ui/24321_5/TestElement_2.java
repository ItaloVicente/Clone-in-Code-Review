		if (children == null) {
			return null;
		}

		return new NodeList() {
			@Override
			public int getLength() {
				return children.size();
			}

			@Override
			public Node item(int index) {
				return children.get(index);
			}
		};
