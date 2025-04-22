        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        CSSConditionalSelectorImpl s = (CSSConditionalSelectorImpl)obj;
        return (s.simpleSelector.equals(simpleSelector) &&
                s.condition.equals(condition));
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Selector#getSelectorType()}.
     */
    @Override
