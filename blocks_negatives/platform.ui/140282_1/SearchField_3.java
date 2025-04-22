			@Override
			protected void handleElementSelected(String string, Object selectedElement) {
				if (selectedElement instanceof QuickAccessElement) {
					QuickAccessElement element = (QuickAccessElement) selectedElement;
					addPreviousPick(string, element);
					element.execute();
