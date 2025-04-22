        if (obj == null || (obj.getClass() != getClass())) {
            return false;
        }
        CSSLangConditionImpl c = (CSSLangConditionImpl)obj;
        return c.lang.equals(lang);
    }

    /**
     * <b>SAC</b>: Implements {@link
     * org.w3c.css.sac.Condition#getConditionType()}.
     */
    @Override
