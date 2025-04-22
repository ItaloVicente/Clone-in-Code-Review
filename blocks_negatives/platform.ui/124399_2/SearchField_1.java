				int arrayIndex = 0;
				for (int i = 0; i < orderedElements.length; i++) {
					QuickAccessProvider quickAccessProvider = providerMap.get(orderedProviders[i]);
					int numTexts = Integer.parseInt(textEntries[i]);
					if (quickAccessProvider != null) {
						QuickAccessElement quickAccessElement = quickAccessProvider
								.getElementForId(orderedElements[i]);
						if (quickAccessElement != null) {
							ArrayList<String> arrayList = new ArrayList<>();
							for (int j = arrayIndex; j < arrayIndex + numTexts; j++) {
								String text = textArray[j];
								if (text.length() > 0) {
									arrayList.add(text);
									elementMap.put(text, quickAccessElement);
