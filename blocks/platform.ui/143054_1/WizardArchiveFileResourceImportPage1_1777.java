			if( ensureTarSourceIsValid()) {
				TarFile tarFile = getSpecifiedTarSourceFile();
				importStructureProvider = new TarLeveledStructureProvider(tarFile);
			}
		} else if(ensureZipSourceIsValid()) {
			ZipFile zipFile = getSpecifiedZipSourceFile();
			importStructureProvider = new ZipLeveledStructureProvider(zipFile);
		}

		if (importStructureProvider == null) {
			return false;
		}
