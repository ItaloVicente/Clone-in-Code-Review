			for (; i < filteredElements.length; i++) {
				Object item = filteredElements[i];
				if (filter != null && filter.getPattern().equals(getElementName(item))) {
					if (isHistoryElement(item)) {
						preparedElements.add(0, item);
						hasHistory = true;
					} else {
						preparedElements.add(item);
					}
				} else {
					break;
				}

				if (reportEvery != 0 && ((i + 1) % reportEvery == 0)) {
					monitor.worked(1);
