            Location instanceLoc = Platform.getInstanceLocation();
            if (instanceLoc != null)
            	instanceLoc.release();
        }
    }

    /**
     * Creates the display used by the application.
     *
     * @return the display used by the application
     */
    protected Display createDisplay() {
        return PlatformUI.createDisplay();
    }

    @Override
