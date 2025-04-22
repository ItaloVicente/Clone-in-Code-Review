	private void searchForReuse(
			final Collection<PackedObjectLoader> reuseLoaders,
			final LocalObjectToPack otp) throws IOException {
		windowCursor.openObjectInAllPacks(otp, reuseLoaders);
		if (reuseDeltas) {
			selectDeltaReuseForObject(otp, reuseLoaders);
		}
		if (reuseObjects && !otp.isCopyable()) {
			selectObjectReuseForObject(otp, reuseLoaders);
		}
	}

	private void selectDeltaReuseForObject(final LocalObjectToPack otp,
			final Collection<PackedObjectLoader> loaders) throws IOException {
		PackedObjectLoader bestLoader = null;
		ObjectId bestBase = null;

		for (PackedObjectLoader loader : loaders) {
			ObjectId idBase = loader.getDeltaBase();
			if (idBase == null)
				continue;
			ObjectToPack otpBase = objectsMap.get(idBase);

			if ((otpBase != null || (thin && edgeObjects.get(idBase) != null))
					&& isBetterDeltaReuseLoader(bestLoader, loader)) {
				bestLoader = loader;
				bestBase = (otpBase != null ? otpBase : idBase);
			}
		}

		if (bestLoader != null) {
			otp.setCopyFromPack(bestLoader);
			otp.setDeltaBase(bestBase);
		}
	}

	private static boolean isBetterDeltaReuseLoader(
			PackedObjectLoader currentLoader, PackedObjectLoader loader)
			throws IOException {
		if (currentLoader == null)
			return true;
		if (loader.getRawSize() < currentLoader.getRawSize())
			return true;
		return (loader.getRawSize() == currentLoader.getRawSize()
				&& loader.supportsFastCopyRawData() && !currentLoader
				.supportsFastCopyRawData());
	}

	private void selectObjectReuseForObject(final LocalObjectToPack otp,
			final Collection<PackedObjectLoader> loaders) {
		for (final PackedObjectLoader loader : loaders) {
			if (loader instanceof WholePackedObjectLoader) {
				otp.setCopyFromPack(loader);
				return;
			}
		}
	}

