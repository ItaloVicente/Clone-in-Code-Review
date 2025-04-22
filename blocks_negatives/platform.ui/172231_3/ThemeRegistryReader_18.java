				if (themeDescriptor != null) {
					themeDescriptor.setData(name, value);
				} else {
					themeRegistry.setData(name, value);
					if (!dataMap.containsKey(name)) {
						dataMap.put(name, value);
					}
