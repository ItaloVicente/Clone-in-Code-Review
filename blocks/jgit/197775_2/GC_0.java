			PackFile realPack = new PackFile(packDir

			repo.getObjectDatabase().closeAllPackHandles(realPack);
			tmpPack.setReadOnly();

			FileUtils.rename(tmpPack

