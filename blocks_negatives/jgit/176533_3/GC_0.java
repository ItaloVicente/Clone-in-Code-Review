			for (PackExt ext : extensions)
				if (PackExt.PACK.equals(ext)) {
					File f = nameFor(packName, "." + ext.getExtension()); //$NON-NLS-1$
					removeOldPack(f, packName, ext, deleteOptions);
					break;
				}
