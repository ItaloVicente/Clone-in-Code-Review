
	private class WWinPartService extends PartService {

		@Override
		public void partActivated(IWorkbenchPart part) {
			super.partActivated(part);
			selectionService.notifyListeners(part);
		}

	}
