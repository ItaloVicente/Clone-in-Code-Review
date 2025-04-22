        if (useAlternateNetwork) {
            AlternateAddress aa = alternateAddresses.get(AlternateAddressType.EXTERNAL);
            if (aa == null) {
                throw new IllegalStateException("external addresses selected, but none found! this is a bug.");
            } else {
                return aa.hostname();
            }
        } else {
            return hostname;
        }
