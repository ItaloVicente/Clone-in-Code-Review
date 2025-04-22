    @Override
    public boolean get(final URIish uri,
                       final CredentialItem... items) throws UnsupportedCredentialItem {
        try {
            return super.get(uri,
                             items);
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
