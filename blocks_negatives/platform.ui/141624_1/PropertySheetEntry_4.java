			for (int j = 0; j < ids.length; j++) {
				Object object = propertyDescriptorMaps[i].get(ids[j]);
				if (object == null ||
						!intersection.get(ids[j])
								.isCompatibleWith((IPropertyDescriptor) object)) {
					intersection.remove(ids[j]);
