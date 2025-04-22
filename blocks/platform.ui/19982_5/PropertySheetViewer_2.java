	private List getTextFilteredEntries(List entries) {
		List textFiltered = new ArrayList();
		for (int i = 0; i < entries.size(); i++) {
			IPropertySheetEntry entry = (IPropertySheetEntry) entries.get(i);
			if ((entry.getDisplayName().toLowerCase().indexOf(textFilter.toLowerCase()) >= 0) ||
			    (entry.getValueAsString().toLowerCase().indexOf(textFilter.toLowerCase()) >= 0)) {
				textFiltered.add(entry);
			}
		}

		return textFiltered;
	}        
   
