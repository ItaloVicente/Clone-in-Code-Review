			for (Iterator it = dirtyParts.iterator(); it.hasNext();) {
				ISaveablePart saveablePart = (ISaveablePart) it.next();
				if (!saveablePart.isSaveOnCloseNeeded()) {
					it.remove();
				}
			}
