    /**
     * Sets the standby state of this intro part. An intro part should render
     * itself differently in the full and standby modes. In standby mode, the
     * part should be partially visible to the user but otherwise allow them
     * to work. In full mode, the part should be fully visible and be the center
     * of the user's attention.
     * <p>
     * This method is automatically called by the workbench at appropriate
     * times. Clients must not call this method directly (call
     * {@link IIntroManager#setIntroStandby(IIntroPart, boolean)} instead.
     * </p>
     *
     * @param standby <code>true</code> to put this part in its partially
     * visible standy mode, and <code>false</code> to make it fully visible
     */
    public void standbyStateChanged(boolean standby);
