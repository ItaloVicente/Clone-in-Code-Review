		synchronized (previousPicksList) {
			previousPicksList.remove(element);
			if (previousPicksList.size() == MAXIMUM_NUMBER_OF_ELEMENTS) {
				Object removedElement = previousPicksList.remove(previousPicksList.size() - 1);
				ArrayList<String> removedList = textMap.remove(removedElement);
				for (int i = 0; i < removedList.size(); i++) {
					elementMap.remove(removedList.get(i));
				}
