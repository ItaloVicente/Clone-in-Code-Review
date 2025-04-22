            CacheWrapper.this.flushCache();
        }
    }

    /**
     * Creates a <code>CacheWrapper</code> with the given parent
     *
     * @param parent
     */
    public CacheWrapper(Composite parent) {
        proxy = new Composite(parent, SWT.NONE);

        proxy.setLayout(new WrapperLayout());
    }

    /**
     * Flush the cache. Call this when the child has changed in order to force
     * the size to be recomputed in the next resize event.
     */
    public void flushCache() {
        cache.flush();
    }

    /**
     * Use this as the parent of the real control.
     *
     * @return the proxy contol. It should be given exactly one child.
     */
    public Composite getControl() {
        return proxy;
    }

    /**
     * Dispose of any widgets created by this wrapper.
     */
    public void dispose() {
        if (proxy != null) {
            proxy.dispose();
            proxy = null;
        }
    }
