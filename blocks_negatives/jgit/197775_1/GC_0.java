			PackFile realPack = new PackFile(packDir, id, PackExt.PACK);

			repo.getObjectDatabase().closeAllPackHandles(realPack);
			tmpPack.setReadOnly();

			FileUtils.rename(tmpPack, realPack, StandardCopyOption.ATOMIC_MOVE);
