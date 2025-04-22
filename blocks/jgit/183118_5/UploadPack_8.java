			} catch (IOException ioe) {
				if (FileUtils.isStaleFileHandleInCausalChain(ioe) &&
						writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					db.getObjectDatabase().refreshPacks();
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
