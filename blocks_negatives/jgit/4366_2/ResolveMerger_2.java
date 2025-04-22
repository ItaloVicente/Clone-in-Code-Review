
		if (result.containsConflicts()) {
			add(tw.getRawPath(), base, DirCacheEntry.STAGE_1);
			add(tw.getRawPath(), ours, DirCacheEntry.STAGE_2);
			add(tw.getRawPath(), theirs, DirCacheEntry.STAGE_3);
			mergeResults.put(tw.getPathString(), result);
			return false;
		} else {
			DirCacheEntry dce = new DirCacheEntry(tw.getPathString());
			dce.setFileMode(tw.getFileMode(0));
			dce.setLastModified(of.lastModified());
			dce.setLength((int) of.length());
			InputStream is = new FileInputStream(of);
			try {
				dce.setObjectId(oi.insert(Constants.OBJ_BLOB, of.length(),
						is));
			} finally {
				is.close();
				if (inCore)
					FileUtils.delete(of);
			}
			builder.add(dce);
			return true;
		}
