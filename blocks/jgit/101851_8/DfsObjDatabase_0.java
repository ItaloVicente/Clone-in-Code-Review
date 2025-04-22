				newPacks.add(new DfsPackFile(cache
				foundNew = true;
			}

			DfsReftable oldReftable = reftables.remove(dsc);
			if (oldReftable != null) {
				newReftables.add(oldReftable);
			} else if (dsc.hasFileExt(PackExt.REFTABLE)) {
				newReftables.add(new DfsReftable(cache
