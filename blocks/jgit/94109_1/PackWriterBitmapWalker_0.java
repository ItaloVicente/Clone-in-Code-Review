				RevObject ro;
				while ((ro = walker.nextObject()) != null) {
					bitmapResult.addObject(ro
					pm.update(1);
				}
			} catch (MissingObjectException e) {
				if (!ignoreMissingStart) {
					throw e;
				}
