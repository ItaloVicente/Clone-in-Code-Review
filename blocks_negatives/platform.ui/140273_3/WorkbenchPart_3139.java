            } catch (RuntimeException e) {
                WorkbenchPlugin.log(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * Subclasses may override this method (however, if they do so, they
     * should invoke the method on their superclass to ensure that the
     * Platform's adapter manager is consulted).
     */
    @Override
