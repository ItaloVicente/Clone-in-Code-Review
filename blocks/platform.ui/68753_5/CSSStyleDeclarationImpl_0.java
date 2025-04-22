		if (cssPropertyListView == null) {
			cssPropertyListView = new CSSPropertyList() {

				@Override
				public int getLength() {
					return properties.size();
				}

				@Override
				public CSSProperty item(int i) {
					return properties.get(i);
				}

			};
