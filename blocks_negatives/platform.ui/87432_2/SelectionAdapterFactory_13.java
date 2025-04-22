		return new IIterable() {

			@Override
			public Iterator iterator() {
				return list.iterator();
			}
		};
