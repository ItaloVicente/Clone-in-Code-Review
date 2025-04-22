    /**
     * Return the standby state of the given intro part.
     *
     * @param part the intro part
     * @return <code>true</code> if the part in its partially
     * visible standy mode, and <code>false</code> if in its fully visible state.
     * <code>false</code> is returned if part is <code>null</code> or it is not
     * the intro part returned by {@link #getIntro()}.
     */
    boolean isIntroStandby(IIntroPart part);
