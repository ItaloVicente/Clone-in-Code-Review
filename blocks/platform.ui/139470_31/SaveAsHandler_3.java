		MPart activeMPart = getActivePart(window);

		IWorkbenchPart activePart = InternalHandlerUtil.getActivePart(context);
		ISaveablePart part = SaveableHelper.getSaveable(activePart);
		if (part == null && activeMPart != null && activeMPart.isDirty()) {
			return EvaluationResult.FALSE;
		}

