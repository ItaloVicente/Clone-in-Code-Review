        super.markDirty();
        IContributionManager parent = getParent();
        if (parent != null) {
            parent.markDirty();
        }
    }

    /**
     * Returns whether the menu control is created
     * and not disposed.
     *
     * @return <code>true</code> if the control is created
     *	and not disposed, <code>false</code> otherwise
