				} catch (IOException e) {
				}
			}
		}

		ArrayList mappingsList = new ArrayList();
		if (bundle != null) {
			boolean found = true;
			int i = 0;
			while (found) {
				try {
					mappingsList.add(bundle.getString(Integer.toString(i)));
				} catch (MissingResourceException e) {
					found = false;
				}
				i++;
			}
		}
		String[] mappings = (String[]) mappingsList.toArray(new String[mappingsList.size()]);
		mappingsMap.put(definingBundle, mappings);
		return mappings;
	}

	private static String[] getMappings(Bundle definingBundle) {
		String[] mappings = (String[]) mappingsMap.get(definingBundle);
		if (mappings == null) {
			mappings = loadMappings(definingBundle);
		}
		if (mappings == null) {
			mappings = new String[0];
		}
		return mappings;
	}

	public ProductProperties(IProduct product) {
		if (product == null) {
