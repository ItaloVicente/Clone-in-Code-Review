                if (event.getBundle() == getBundle()) {
                    if (event.getType() == BundleEvent.STARTED) {
                        if (getBundle().getState() == Bundle.ACTIVE) {
                            refreshPluginActions();
                        }
                        try {
                            fc.removeBundleListener(this);
                        } catch (IllegalStateException ex) {
                        }
                    }
                }
            }
        };
        context.addBundleListener(bundleListener);
    }

    /**
     * The <code>AbstractUIPlugin</code> implementation of this {@link Plugin}
     * method saves this plug-in's preference and dialog stores and shuts down
     * its image registry (if they are in use). Subclasses may extend this
     * method, but must send super <b>last</b>. A try-finally statement should
     * be used where necessary to ensure that <code>super.stop()</code> is
     * always done.
     * {@inheritDoc}
     *
     * @since 3.0
     */
    @Override
