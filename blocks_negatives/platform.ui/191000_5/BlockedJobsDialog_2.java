		return new ProgressViewerContentProvider(viewer, true, false) {
			@Override
			public Object[] getElements(Object inputElement) {
				Object[] elements = super.getElements(inputElement);
				Object[] result = new Object[elements.length + 1];
				System.arraycopy(elements, 0, result, 1, elements.length);
				result[0] = blockedElement;
				return result;
			}
		};
