		if (value == null) {
			return MessageUtil.getString("email_address_is_incomplete"); //$NON-NLS-1$
		}
		String emailAddress = (String) value;
		int index = emailAddress.indexOf('@');
		int length = emailAddress.length();
		if (index > 0 && index < length) {
			return null;
		}
		return MessageUtil
				.getString("email_address_does_not_have_a_valid_format"); //$NON-NLS-1$
	}
