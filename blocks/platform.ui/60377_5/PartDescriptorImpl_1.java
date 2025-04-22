	public List<String> getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList<String>(String.class, this, BasicPackageImpl.PART_DESCRIPTOR__VARIABLES);
		}
		return variables;
	}

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = new EcoreEMap<String,String>(ApplicationPackageImpl.Literals.STRING_TO_STRING_MAP, StringToStringMapImpl.class, this, BasicPackageImpl.PART_DESCRIPTOR__PROPERTIES);
		}
		return properties.map();
	}

