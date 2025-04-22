		if (P_ID_POSTALCODE.equals(name)) {
			setPostalCode((String) value);
			return;
		}
		if (P_ID_CITY.equals(name)) {
			setCity((String) value);
			return;
		}
		if (P_ID_PROVINCE.equals(name)) {
			setProvince((Integer) value);
			return;
		}
	}

	private void setProvince(Integer newProvince) {
		province = newProvince;
	}

	private void setStreet(StreetAddress newStreet) {
		street = newStreet;
	}

	@Override
