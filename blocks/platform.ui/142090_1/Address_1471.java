		if (propKey.equals(P_ID_PROVINCE))
			return getProvince();
		if (propKey.equals(P_ID_STREET))
			return getStreet();
		if (propKey.equals(P_ID_CITY))
			return getCity();
		if (propKey.equals(P_ID_POSTALCODE))
			return getPostalCode();
		return null;
	}

	private Integer getProvince() {
		if (province == null)
			province = PROVINCE_DEFAULT;
		return province;
	}

	public StreetAddress getStreet() {
		if (street == null)
			street = new StreetAddress();
		return street;
	}

	@Override
