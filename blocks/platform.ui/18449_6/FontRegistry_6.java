    
    public void remove(String symbolicName) {
    	Assert.isNotNull(symbolicName);
    	
        FontData[] existing = stringToFontData.remove(symbolicName);
        if (existing == null) {
        	return;
 		}

 		fireMappingChanged(symbolicName, existing, null);

        FontRecord oldFont = stringToFontRecord.remove(symbolicName);
        if (oldFont != null) {
 			oldFont.addAllocatedFontsToStale(defaultFontRecord().getBaseFont());
 		}
    }
