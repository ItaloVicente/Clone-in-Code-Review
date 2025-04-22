        }
    }

    /**
     * Creates an accumulating progress monitor wrapping the given one
     * that uses the given display.
     *
     * @param monitor the actual progress monitor to be wrapped
     * @param display the SWT display used to forward the calls
     *  to the wrapped progress monitor
     */
    public AccumulatingProgressMonitor(IProgressMonitor monitor, Display display) {
        super(monitor);
        Assert.isNotNull(display);
        this.display = display;
    }

    @Override
