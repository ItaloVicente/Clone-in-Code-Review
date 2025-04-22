			} catch (UploadPackSendPackException uploadPackSendPackException) {
				db.getObjectDatabase()
					.refreshPackList(uploadPackSendPackException);
				continue;
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
