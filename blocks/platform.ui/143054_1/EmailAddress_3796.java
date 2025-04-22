		if (name.equals(P_ID_USERID)) {
			setUserid((String) value);
			return;
		}
		if (name.equals(P_ID_DOMAIN)) {
			setDomain((String) value);
			return;
		}
	}

	private void setUserid(String newUserid) {
		userid = newUserid;
	}

	@Override
