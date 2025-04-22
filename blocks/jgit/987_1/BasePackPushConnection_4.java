			for (final Ref r : getRefs())
				remoteObjects.add(r.getObjectId());
			remoteObjects.addAll(additionalHaves);
			for (final RemoteRefUpdate r : refUpdates.values()) {
				if (!ObjectId.zeroId().equals(r.getNewObjectId()))
					newObjects.add(r.getNewObjectId());
			}

			writer.setThin(thinPack);
			writer.setDeltaBaseAsOffset(capableOfsDelta);
			writer.preparePack(newObjects
			start = System.currentTimeMillis();
			writer.writePack(out);
		} finally {
			writer.release();
		}
