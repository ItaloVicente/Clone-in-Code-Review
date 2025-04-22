        setDefaultPageImageDescriptor(WorkbenchImages
                .getImageDescriptor(IWorkbenchGraphicConstants.IMG_WIZBAN_NEW_WIZ));
        setNeedsProgressMonitor(true);
    }

    /**
     * The user has pressed Finish. Instruct self's pages to finish, and answer
     * a boolean indicating success.
     *
     * @return boolean
     */
    @Override
