		}

		if (name.indexOf('/') < 0 && !HEAD.equals(name)) {
			return true;
		}

		if (name.length() > txnCommitted.length()
