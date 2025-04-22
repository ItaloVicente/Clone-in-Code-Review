		fWindow.removePerspectiveListener(this);
		super.doTearDown();
	}

	public void testPerspectiveActivated() {
		 fPageMask = fWindow.getActivePage();
		 fPerMask = fWorkbench.getPerspectiveRegistry().findPerspectiveWithId(EmptyPerspective.PERSP_ID );
		 fPageMask.setPerspective( fPerMask );

		 assertEquals( isActivated( fEvent ), true );
	}

	public void testPerspectiveChanged() {
		 fPageMask = fWindow.getActivePage();
		 fPerMask = fWorkbench.getPerspectiveRegistry().findPerspectiveWithId(EmptyPerspective.PERSP_ID );
		 fPageMask.setPerspective( fPerMask );

		 assertEquals( isActivated( fEvent ), true );
	}

	@Override
