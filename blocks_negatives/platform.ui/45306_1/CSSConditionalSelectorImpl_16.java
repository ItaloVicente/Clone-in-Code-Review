        return ((ExtendedSelector)getSimpleSelector()).getSpecificity() +
               ((ExtendedCondition)getCondition()).getSpecificity();
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.ConditionalSelector#getSimpleSelector()}.
     */
    @Override
