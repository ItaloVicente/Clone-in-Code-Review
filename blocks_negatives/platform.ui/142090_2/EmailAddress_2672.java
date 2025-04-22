        if (propKey.equals(P_ID_USERID))
            return getUserid();
        if (propKey.equals(P_ID_DOMAIN))
            return getDomain();
        return null;
    }

    /**
     * Returns the userid
     */
    private String getUserid() {
        if (userid == null)
            userid = USERID_DEFAULT;
        return userid;
    }

    @Override
