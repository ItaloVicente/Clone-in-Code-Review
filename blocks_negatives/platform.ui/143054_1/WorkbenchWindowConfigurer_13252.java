    }

    /**
     * Creates a new workbench window configurer.
     * <p>
     * This method is declared package-private. Clients obtain instances
     * via {@link WorkbenchAdvisor#getWindowConfigurer
     * WorkbenchAdvisor.getWindowConfigurer}
     * </p>
     *
     * @param window the workbench window that this object configures
     * @see WorkbenchAdvisor#getWindowConfigurer
     */
    WorkbenchWindowConfigurer(WorkbenchWindow window) {
        if (window == null) {
            throw new IllegalArgumentException();
        }
        this.window = window;
        windowTitle = WorkbenchPlugin.getDefault().getProductName();
        if (windowTitle == null) {
        }
    }

    @Override
