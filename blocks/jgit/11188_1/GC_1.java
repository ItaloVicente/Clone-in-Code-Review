
			if (realPack.exists())
				for (PackFile p : repo.getObjectDatabase().getPacks())
					if (realPack.getPath().equals(p.getPackFile().getPath())) {
						p.close();
						break;
					}
