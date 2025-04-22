			if (save) {
				if (part instanceof ISaveablePart) {
					ISaveablePart saveablePart = (ISaveablePart) part;
					if (!saveablePart.isSaveOnCloseNeeded()) {
						continue;
					}
