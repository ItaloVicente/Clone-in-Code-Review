        return Workbench.getInstance().getWorkbenchConfigurer();
    }

    /**
     * Returns the title as set by <code>setTitle</code>, without consulting the shell.
     *
     * @return the window title as set, or <code>null</code> if not set
     */
    /* package */String basicGetTitle() {
        return windowTitle;
    }

    @Override
