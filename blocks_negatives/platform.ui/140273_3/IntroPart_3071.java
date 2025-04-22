                }
            });
        }
    }

    /**
     * This implementation of the method declared by <code>IAdaptable</code>
     * passes the request along to the platform's adapter manager; roughly
     * <code>Platform.getAdapterManager().getAdapter(this, adapter)</code>.
     * Subclasses may override this method (however, if they do so, they should
     * invoke the method on their superclass to ensure that the Platform's
     * adapter manager is consulted).
     */
    @Override
