        fWindow.removePerspectiveListener(this);
        super.doTearDown();
    }

    public void testPerspectiveActivated() {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *
         fPageMask = fWindow.getActivePage();
         fPerMask = fWorkbench.getPerspectiveRegistry().findPerspectiveWithId(EmptyPerspective.PERSP_ID );
         fPageMask.setPerspective( fPerMask );

         assertEquals( isActivated( fEvent ), true );
         */
    }

    public void testPerspectiveChanged() {
        /*
         * Commented out because until test case can be updated to work
         * with new window/page/perspective implementation
         *
         fPageMask = fWindow.getActivePage();
         fPerMask = fWorkbench.getPerspectiveRegistry().findPerspectiveWithId(EmptyPerspective.PERSP_ID );
         fPageMask.setPerspective( fPerMask );

         assertEquals( isActivated( fEvent ), true );
         */
    }

    /**
     * @see IPerspectiveListener#perspectiveActivated(IWorkbenchPage, IPerspectiveDescriptor)
     */
    @Override
