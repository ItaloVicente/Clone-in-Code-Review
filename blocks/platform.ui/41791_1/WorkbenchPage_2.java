			IWorkbenchPart part = reference.getPart(false);
			ISaveablePart saveable = SaveableHelper.getSaveable(part);
			if (part != null) {
				if (saveable.isDirty()) {
					result.add(part);
				}
			}
		}
		return result.toArray(new IWorkbenchPart[result.size()]);
