				s = new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						return v.equals(element.getElementId());
					}
				};
