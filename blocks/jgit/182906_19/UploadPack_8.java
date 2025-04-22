			} catch (StaleFileHandleOnPackfile staleFileHandleOnPackfile) {
				if (writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					db.getObjectDatabase()
						.refreshPackList(
							staleFileHandleOnPackfile.getPack()
							staleFileHandleOnPackfile.getOriginalException());
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
