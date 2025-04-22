			
			Map<String, List<TabDescriptor>> mapOfAfterTab = new HashMap<String, List<TabDescriptor>>();
						
			for (int j = topOfCategory; j < endOfCategory; j++) {				
				TabDescriptor tab = tabs.get(j);
				String afterTab;
				if((afterTab = tab.getAfterTab()) == "" ){ //$NON-NLS-1$
					afterTab = "no after tab"; //$NON-NLS-1$
