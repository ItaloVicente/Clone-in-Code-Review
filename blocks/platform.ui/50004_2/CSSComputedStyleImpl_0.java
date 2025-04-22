
			CSSValue value = property.getValue();
			String name = property.getName();
			if ("preferences".equals(name) && value instanceof CSSValueListImpl) {
				CSSValueListImpl valueList = (CSSValueListImpl) value;
				CSSProperty myProperty = findProperty(name);
				if (myProperty == null) {
					super.addProperty(property);
				} else {
					CSSValue myValue = myProperty.getValue();
					if (myValue instanceof CSSValueListImpl) {
						CSSValueListImpl myValueList = (CSSValueListImpl) myValue;
						myValueList.addAll(valueList);
					} else {
						super.removeProperty(name);
						super.addProperty(property);
					}

				}
			} else {
				super.removeProperty(name);
				super.addProperty(property);
			}
		}
	}

	private CSSProperty findProperty(String name) {
		if (name == null) {
			return null;
		}
		CSSPropertyList list = this.getCSSPropertyList();
		for (int i = 0; i < list.getLength(); i++) {
			CSSProperty item = list.item(i);
			if (name.equals(item.getName())) {
				return item;
			}
