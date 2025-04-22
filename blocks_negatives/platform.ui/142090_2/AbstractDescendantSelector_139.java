        return ((ExtendedSelector)ancestorSelector).getSpecificity() +
               ((ExtendedSelector)simpleSelector).getSpecificity();
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.DescendantSelector#getAncestorSelector()}.
     */
    @Override
