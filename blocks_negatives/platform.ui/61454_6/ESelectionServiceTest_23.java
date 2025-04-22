		ESelectionService selectionService = (ESelectionService) partContextA
				.get(ESelectionService.class.getName());
		selectionService.addSelectionListener(partC.getElementId(),
				new ISelectionListener() {
					@Override
					public void selectionChanged(MPart part, Object selection) {
						partOneImpl.setOtherSelection(selection);
					}
				});
