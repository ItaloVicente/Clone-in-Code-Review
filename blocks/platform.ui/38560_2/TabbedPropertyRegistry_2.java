			
			
			for(int k = 0; k < endOfCategory - topOfCategory +1;k++){
				if(categoryList.size() > k){
					TabDescriptor current = categoryList.get(k);
					if((toAdd = mapOfAfterTab.get(current.getId())) != null){
						categoryList.addAll(toAdd);
						mapOfAfterTab.remove(current.getId());
					}
				} else {
					if(mapOfAfterTab.keySet().size() != 0){
						String key = mapOfAfterTab.keySet().iterator().next();
						categoryList.addAll(mapOfAfterTab.get(key));
						mapOfAfterTab.remove(key);
