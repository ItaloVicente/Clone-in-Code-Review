        }        boolean seenNonXattr = false;

        for (MutationSpec spec : mutationSpecs) {
            if (spec.xattr() && seenNonXattr) {
                throw new XattrOrderingException("Xattr-based commands must always come first in the builder!");
            } else if (!spec.xattr()) {
                seenNonXattr = true;
            }
