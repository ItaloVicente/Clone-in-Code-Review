								realExt));
					}
				}

			} finally {
				if (delete) {
					if (tmpPack.exists())
						tmpPack.delete();
					for (File tmpExt : tmpExts.values()) {
						if (tmpExt.exists())
							tmpExt.delete();
