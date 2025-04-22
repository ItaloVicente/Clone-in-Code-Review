			try {
				while (walker.next() != null) {
					pm.update(1);
					countOfBitmapIndexMisses++;
				}

				RevObject ro;
				while ((ro = walker.nextObject()) != null) {
					bitmapResult.addObject(ro, ro.getType());
					pm.update(1);
				}
			} catch (MissingObjectException e) {
				if (!ignoreMissingStart) {
					throw e;
				}
