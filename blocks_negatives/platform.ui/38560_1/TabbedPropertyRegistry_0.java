			for (int j = topOfCategory; j < endOfCategory; j++) {
				TabDescriptor tab = (TabDescriptor) tabs.get(j);
				if (tab.getAfterTab().equals(TOP)) {
					categoryList.add(0, tabs.get(j));
				} else {
					categoryList.add(tabs.get(j));
