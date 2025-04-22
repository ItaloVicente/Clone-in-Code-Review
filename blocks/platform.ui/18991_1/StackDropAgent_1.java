		if (!(dragElement instanceof MPart)) {
			EModelService ms = dndManager.getModelService();
			MWindow dragElementWin = ms.getTopLevelWindowFor(dragElement);
			MWindow dropWin = ms.getTopLevelWindowFor(stack);
			if (dragElementWin != dropWin)
				return false;
		}

