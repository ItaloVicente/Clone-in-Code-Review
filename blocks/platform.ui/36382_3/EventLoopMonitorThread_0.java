		private boolean doesEventIndicateResponsiveUI(int eventType) {
			switch (eventType) {
			case SWT.Skin:
			case SWT.MeasureItem:
			case SWT.Dispose:
				return false;
			default:
				return true;
			}
		}

