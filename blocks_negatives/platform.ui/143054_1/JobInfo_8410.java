        return true;
    }

    /**
     * Set the description of the blocking status.
     *
     * @param blockedStatus
     *            The IStatus that describes the blockage or <code>null</code>
     */
    public void setBlockedStatus(IStatus blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    /**
     * Set the GroupInfo to be the group.
     *
     * @param group
     */
    void setGroupInfo(GroupInfo group) {
        parent = group;
    }

    /**
     * Set the name of the taskInfo.
     *
     * @param name
     */
    void setTaskName(String name) {
        taskInfo.setTaskName(name);
    }

    /**
     * Set the number of ticks this job represents. Default is indeterminate
     * (-1).
     *
     * @param ticks
     *            The ticks to set.
     */
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
