	@Override
	public boolean get(final URIish uri
		try {
			return super.get(uri
		} catch (UnsupportedCredentialItem e) {
			for (CredentialItem i : items) {
				if (i instanceof CredentialItem.YesNoType) {
					((CredentialItem.YesNoType) i).setValue(true);
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}
