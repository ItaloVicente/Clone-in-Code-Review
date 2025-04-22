			} catch (TriggerRefreshPackListException e) {
				db.getObjectDatabase()
					.refreshPackList(e);
				continue;
			} finally {
				statistics = pw.getStatistics();
				if (statistics != null) {
					postUploadHook.onPostUpload(statistics);
				}
				pw.close();
