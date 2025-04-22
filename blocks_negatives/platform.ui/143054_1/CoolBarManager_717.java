        if (!coolBarExist()) {
            return;
        }
        coolBar.setLocked(value);
    }

    /**
     * Subclasses may extend this <code>IContributionManager</code> method,
     * but must call <code>super.update</code>.
     *
     * @see org.eclipse.jface.action.IContributionManager#update(boolean)
     */
    @Override
