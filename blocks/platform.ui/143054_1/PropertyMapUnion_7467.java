		PropertyInfo info = new PropertyInfo(newValue, true);

		values.put(propertyId, info);
	}

	public void addMap(IPropertyMap toAdd) {
		Set keySet = toAdd.keySet();

		for (Iterator iter = keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			PropertyInfo localInfo = (PropertyInfo) values.get(key);
			if (localInfo != null) {
				if (toAdd.propertyExists(key)) {
					Object value = toAdd.getValue(key, Object.class);

					if (!Objects.equals(value, toAdd.getValue(key, Object.class))) {
						localInfo.value = null;
					}

					localInfo.commonAttribute = localInfo.commonAttribute && toAdd.isCommonProperty(key);
				} else {
					localInfo.commonAttribute = false;
				}
			}
		}

		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String element = (String) iter.next();

			PropertyInfo localInfo = (PropertyInfo) values.get(element);
			if (localInfo == null) {
				Object value = toAdd.getValue(element, Object.class);

				boolean isCommon = toAdd.isCommonProperty(element);

				localInfo = new PropertyInfo(value, isCommon);
				values.put(element, localInfo);
			}
		}
	}

	public void removeValue(String propertyId) {
		values.remove(propertyId);
	}
