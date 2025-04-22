				keywordCache.put(element, keywordCollection);
			}
			if (!keywordCollection.isEmpty()){
				Iterator keywords = keywordCollection.iterator();
				while (keywords.hasNext()) {
					keywordList.add(keywords.next());
				}
