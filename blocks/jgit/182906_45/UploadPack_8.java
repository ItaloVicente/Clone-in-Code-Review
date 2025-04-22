			} catch (TriggerRefreshPackListException e) {
				db.getObjectDatabase()
					.refreshPackList();
				continue;
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
