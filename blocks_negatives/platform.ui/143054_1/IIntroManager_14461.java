    /**
     * Sets the standby state of the given intro part. Intro part usually should
     * render themselves differently in the full and standby modes. In standby
     * mode, the part should be partially visible to the user but otherwise
     * allow them to work. In full mode, the part should be fully visible and
     * be the center of the user's attention.
     * <p>
     * This method does nothing if the part is <code>null</code> or is not
     * the intro part returned by {@link #getIntro()}.
     * </p>
     *
     * @param part the intro part, or <code>null</code>
     * @param standby <code>true</code> to put the part in its partially
     * visible standy mode, and <code>false</code> to make it fully visible.
     */
    public void setIntroStandby(IIntroPart part, boolean standby);
