			boolean delete = true;
			try {
				FileUtils.rename(tmpPack, realPack);
				delete = false;
				for (Map.Entry<PackExt, File> tmpEntry : tmpExts.entrySet()) {
					File tmpExt = tmpEntry.getValue();
					tmpExt.setReadOnly();

					File realExt = nameFor(
							id, "." + tmpEntry.getKey().getExtension()); //$NON-NLS-1$
