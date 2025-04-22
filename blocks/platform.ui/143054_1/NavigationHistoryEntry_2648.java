				}

				if (location != null) {
					if (locationMemento != null) {
						location.setInput(editorInfo.editorInput);
						location.restoreState(locationMemento);
						locationMemento = null;
					}
					location.restoreLocation();
				}
			} catch (PartInitException e) {
			}
		}
	}

	String getHistoryText() {
		if (location != null) {
			String text = location.getText();
			if ((text == null) || text.isEmpty()) {
				text = historyText;
			} else {
				historyText = text;
			}
			return text;
		}
