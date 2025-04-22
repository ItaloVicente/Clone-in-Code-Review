			v = null;
		} else {
			v = getOverrides().getVisible(item); 
		}
		
		if (v != null) {
			return v.booleanValue();
