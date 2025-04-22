		@Override
		public QuickAccessElement[] getElements(String filter, IProgressMonitor monitor) {
			if (canDelegate()) {
				if (computer instanceof IQuickAccessComputerExtension) {
					return ((IQuickAccessComputerExtension) computer).computeElements(filter, monitor);
				}
				return null;
			}
			return activateElement;
		}

