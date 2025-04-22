
			if (view != null && view instanceof MarkerView) {
				StructuredSelection selection = new StructuredSelection(marker);
				MarkerView markerView = (MarkerView) view;
				markerView.setSelection(selection, true);
				returnValue = true;

			}
