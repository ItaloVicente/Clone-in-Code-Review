			} catch (IOException ioe) {
				if (FileUtils.isStaleFileHandleInCausalChain(ioe) &&
						writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
