			if (changedElement instanceof MPerspectiveStack) {
				showStack(false);
				return;
			}

			if (changedElement instanceof MCompositePart) {
				MPart innerPart = getLeafPart(changedElement);
				if (innerPart != null) {
					fixToolItemSelection();
					return;
				}
			}
