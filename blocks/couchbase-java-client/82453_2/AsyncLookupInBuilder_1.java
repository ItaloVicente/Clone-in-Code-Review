        boolean seenNonXattr = false;
        for (LookupSpec spec : specs) {
            if (spec.xattr() && seenNonXattr) {
                throw new XattrOrderingException("Xattr-based commands must always come first in the builder!");
            } else if (!spec.xattr()) {
                seenNonXattr = true;
            }
        }
