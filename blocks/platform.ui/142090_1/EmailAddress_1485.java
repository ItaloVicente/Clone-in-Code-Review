		return;
	}

	private void setDomain(java.lang.String newDomain) {
		domain = newDomain;
	}

	private void setEmailAddress(String emailAddress)
			throws IllegalArgumentException {
		if (emailAddress == null)
			throw new IllegalArgumentException(MessageUtil
					.getString("emailaddress_cannot_be_set_to_null")); //$NON-NLS-1$
		int index = emailAddress.indexOf('@');
		int length = emailAddress.length();
		if (index > 0 && index < length) {
			setUserid(emailAddress.substring(0, index));
			setDomain(emailAddress.substring(index + 1));
			return;
		}
		throw new IllegalArgumentException(
				MessageUtil
						.getString("invalid_email_address_format_should_have_been_validated")); //$NON-NLS-1$
	}

	@Override
