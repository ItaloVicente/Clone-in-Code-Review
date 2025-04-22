		if (P_ID_POSTALCODE.equals(property)) {
			setPostalCode(POSTALCODE_DEFAULT);
			return;
		}
		if (P_ID_CITY.equals(property)) {
			setCity(CITY_DEFAULT);
			return;
		}
		if (P_ID_PROVINCE.equals(property)) {
			setProvince(PROVINCE_DEFAULT);
			return;
		}
		if (P_ID_STREET.equals(property)) {
			setStreet(new StreetAddress());
			return;
		}
	}

	private void setCity(String newCity) {
		city = newCity;
	}

	private void setPostalCode(String newPostalCode) {
		this.postalCode = newPostalCode.toUpperCase();
	}

	@Override
