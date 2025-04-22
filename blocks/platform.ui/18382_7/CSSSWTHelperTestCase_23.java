				@Override
				public float getFloatValue(short valueType) throws DOMException {
					return Float.parseFloat(getCssText());
				}
			};
		}
		return null;
	}
