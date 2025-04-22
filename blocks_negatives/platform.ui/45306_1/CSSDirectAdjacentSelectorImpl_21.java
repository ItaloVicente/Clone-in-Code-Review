    /**
     * Creates a new CSSDirectAdjacentSelector object.
     */
    public CSSDirectAdjacentSelectorImpl(short type,
                                     Selector parent,
                                     SimpleSelector simple) {
        super(type, parent, simple);
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Selector#getSelectorType()}.
     */
    @Override
