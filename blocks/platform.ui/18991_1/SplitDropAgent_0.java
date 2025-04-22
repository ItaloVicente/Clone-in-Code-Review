		if (!(dragElement instanceof MPart)) {
			EModelService ms = dndManager.getModelService();
			MWindow dragElementWin = ms.getTopLevelWindowFor(dragElement);
			MWindow dropWin = ms.getTopLevelWindowFor(dropStack);
			if (dragElementWin != dropWin)
				return false;
		}

