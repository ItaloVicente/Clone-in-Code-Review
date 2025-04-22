			if (!(objElement instanceof MMenuElement)) {
				return;
			}

			MMenuElement menuModel = (MMenuElement) objElement;
			MUIElement menuParent = modelService.getContainer(menuModel);
			if (!(menuParent instanceof MPart))
				return;
