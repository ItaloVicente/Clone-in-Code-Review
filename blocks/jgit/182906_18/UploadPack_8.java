			} catch (PackNotFoundException packNotFoundException) {
				if (writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					db.getObjectDatabase()
						.refreshPackList(
							packNotFoundException.getPack()
							packNotFoundException.getOriginalException());
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
