			} else {
				final InputStream oursStream = db.open(ours.getEntryObjectId())
						.openStream();
				final InputStream theirsStream = db.open(
						theirs.getEntryObjectId()).openStream();
				InputStream baseStream = null;
				if (nonTree(modeB))
					baseStream = db.open(base.getEntryObjectId()).openStream();

				final String filePath = tw.getPathString();
				final MergeDriver driver = findMergeDriver(getRepository()
						filePath

				final ByteArrayOutputStream output = new ByteArrayOutputStream();
				boolean success = false;
				try {
					success = driver.merge(getRepository().getConfig()
							oursStream
							commitNames);
				} finally {
					if (oursStream != null)
						oursStream.close();
					if (theirsStream != null)
						theirsStream.close();
					if (baseStream != null)
						baseStream.close();
				}
