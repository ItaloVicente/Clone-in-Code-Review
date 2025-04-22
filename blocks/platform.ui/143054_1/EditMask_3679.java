			if (isInsertingNewCharacter(newRawText) || replacedSelectedText) {
				int selectionDelta =
					editMaskParser.getNextInputPosition(oldSelection)
					- oldSelection;
				if (selectionDelta == 0) {
					selectionDelta = editMaskParser.getNextInputPosition(selection)
					- selection;
				}
				selection += selectionDelta;
			}
