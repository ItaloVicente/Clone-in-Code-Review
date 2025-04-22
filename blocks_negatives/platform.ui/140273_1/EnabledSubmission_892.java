            }
        }

        return compareTo;
    }

    /**
     * Returns the identifier of the part that must be active for this request
     * to be considered.
     *
     * @return the identifier of the part that must be active for this request
     *         to be considered. May be <code>null</code>.
     */
    public String getActivePartId() {
        return activePartId;
    }

    /**
     * Returns the shell that must be active for this request to be considered.
     *
     * @return the shell that must be active for this request to be considered.
     *         May be <code>null</code>.
     */
    public Shell getActiveShell() {
        return activeShell;
    }

    /**
     * Returns the workbench part site of the part that must be active for this
     * request to be considered.
     *
     * @return the workbench part site of the part that must be active for this
     *         request to be considered. May be <code>null</code>.
     */
    public IWorkbenchPartSite getActiveWorkbenchPartSite() {
        return activeWorkbenchPartSite;
    }

    /**
     * Returns the identifier of the context to be enabled.
     *
     * @return the identifier of the context to be enabled. Guaranteed not to be
     *         <code>null</code>.
     */
    public String getContextId() {
        return contextId;
    }

    /**
     * @see Object#toString()
     */
    @Override
