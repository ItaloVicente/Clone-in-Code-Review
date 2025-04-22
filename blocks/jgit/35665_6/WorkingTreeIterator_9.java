	private boolean hasIdentAttrChanged(Set<Attribute> indexAttrs
			Set<Attribute> workingTreeAttrs) {
		Attribute workingIdentAttr = Attributes
				.getIdentAttribute(workingTreeAttrs);
		Attribute indexIdentAttr = Attributes.getIdentAttribute(indexAttrs);
		if (workingIdentAttr == null)
			return indexIdentAttr != null;
		else
			return !workingIdentAttr.equals(indexIdentAttr);
	}

