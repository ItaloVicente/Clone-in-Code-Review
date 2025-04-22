				if (part instanceof ISaveablePart2) {
					ISaveablePart2 saveablePart2 = (ISaveablePart2) part;
					int response = SaveableHelper.savePart(saveablePart2, window, true);
					if (response == ISaveablePart2.CANCEL) {
						return null;
					} else if (response != ISaveablePart2.DEFAULT) {
						continue;
					}
