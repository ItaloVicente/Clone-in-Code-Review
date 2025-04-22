				if (msgOut != NullOutputStream.INSTANCE) {
					String msg = pw.getStatistics().getMessage() + '\n';
					msgOut.write(Constants.encode(msg));
					msgOut.flush();
				}

			} catch (PackNotFoundException packNotFoundException) {
				if (writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					db.getObjectDatabase().refreshPackList(packNotFoundException.getPack()
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
