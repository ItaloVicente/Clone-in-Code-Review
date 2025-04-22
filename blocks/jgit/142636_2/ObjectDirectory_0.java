			if (oldPack != null) {
				if (!oldPack.getFileSnapshot().isModified(packFile)) {
					list.add(oldPack);
					continue;
				}

				oldPack.close();
