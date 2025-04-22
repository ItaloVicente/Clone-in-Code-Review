        } catch (CoreException exception) {
            handleCoreException(exception);
            return false;
        }
        return false;
    }

    /**
     * Gets the label provider and creates it if it does not exist yet.
     * Throws a CoreException if there is a problem
     * creating the labelProvider.
     * This method should not be called unless a check for
     * enabled to be true is done first.
     * @return Returns a ILabelDecorator
     */
    protected abstract IBaseLabelProvider internalGetLabelProvider()
            throws CoreException;

    /**
     * A CoreException has occured. Inform the user and disable
     * the receiver.
     */

    protected void handleCoreException(CoreException exception) {

        WorkbenchPlugin.log(exception);
        crashDisable();
    }

    /**
     * A crash has occured. Disable the receiver without notification.
     */
    public void crashDisable() {
        this.enabled = false;
    }

    /**
     * Return whether or not this is a full or lightweight definition.
     * @return <code>true</code> if this is not a lightweight decorator.
     */
    public abstract boolean isFull();
