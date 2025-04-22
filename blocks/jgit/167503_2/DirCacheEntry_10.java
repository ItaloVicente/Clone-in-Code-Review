			if (version == DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
					&& previous != null) {
				System.arraycopy(previous.path
						previous.path.length - toRemove);
				IO.readFully(in
						pathLen - (previous.path.length - toRemove));
				md.update(path
						pathLen - (previous.path.length - toRemove));
				pathLen = pathLen - (previous.path.length - toRemove);
			} else {
				IO.readFully(in
				md.update(path
