
    /**
     * Returns:
     *  - the value retrieved by a successful GET.
     *  - null for an unsuccessful GET (see the {@link #status()} for details).
     *  - true/false for an EXIST (equivalent to {@link #exists()}).
     *
     * Throws:
     *  - a {@link RuntimeException} if the client side parsing of the result failed ({@link #isFatal()}).
     *
     * @return the value
     * @see #value() for a version that just returns the exception instead of throwing it.
     */
    public Object valueOrThrow() {
        if (isFatal()) {
            throw (RuntimeException) value;
        }
        return value;
    }
