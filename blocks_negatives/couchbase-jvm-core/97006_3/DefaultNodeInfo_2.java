        if (useAlternateNetwork != null) {
            AlternateAddress aa = alternateAddresses.get(useAlternateNetwork);
            if (aa == null) {
                throw new IllegalStateException("external addresses selected, but none found! this is a bug.");
            } else {
                return aa.sslServices().isEmpty() ? sslServices : aa.sslServices();
            }
        } else {
            return sslServices;
        }
