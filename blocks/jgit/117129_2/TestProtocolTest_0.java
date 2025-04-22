			UploadPack up = new UploadPack(db);
			up.setPostUploadHook(new PostUploadHook() {
				@Override
				public void onPostUpload(PackStatistics stats) {
					havesCount = stats.getHaves();
				}
			});
			return up;
