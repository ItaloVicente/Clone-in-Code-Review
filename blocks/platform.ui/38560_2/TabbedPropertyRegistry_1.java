				List<TabDescriptor> tempList = mapOfAfterTab.get(afterTab);
				if(tempList == null){
					tempList = new ArrayList<TabDescriptor>();
					mapOfAfterTab.put(afterTab, tempList);
				}
				tempList.add(tab);
			}		
			
			List<TabDescriptor> toAdd;
			if((toAdd = mapOfAfterTab.get(TOP)) != null){
				categoryList.addAll(toAdd);
				mapOfAfterTab.remove(TOP);
			}
			if((toAdd = mapOfAfterTab.get("no after tab")) != null){ //$NON-NLS-1$
				categoryList.addAll(toAdd);
				mapOfAfterTab.remove("no after tab"); //$NON-NLS-1$
