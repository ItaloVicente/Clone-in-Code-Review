				}
				if (packOut != null && (keepEmpty || entryCount > 0))
					packOut.getChannel().force(true);

				packDigest = null;
				baseById = null;
				baseByPos = null;
