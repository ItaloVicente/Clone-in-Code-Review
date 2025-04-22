			if (selectedItem instanceof MStackElement) {
				EPartService partService = page.getWorkbenchWindow().getService(EPartService.class);
				partService.showPart(((MStackElement) selectedItem).getElementId(), PartState.ACTIVATE);

				return;
			}
