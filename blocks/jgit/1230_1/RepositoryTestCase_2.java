			DirCacheIterator dcIt = tw.getTree(0
			sb.append("["+tw.getPathString()+"
			int stage = dcIt.getDirCacheEntry().getStage();
			if (stage != 0)
				sb.append("
			if (0 != (includedOptions & MOD_TIME)) {
				sb.append("
					timeStamps.headSet(Long.valueOf(dcIt.getDirCacheEntry().getLastModified())).size());
			}
			if (0 != (includedOptions & SMUDGE))
				if (dcIt.getDirCacheEntry().isSmudged())
					sb.append("
			if (0 != (includedOptions & LENGTH))
				sb.append("
						+ Integer.toString(dcIt.getDirCacheEntry().getLength()));
			if (0 != (includedOptions & CONTENT_ID))
				sb.append("
								.getEntryObjectId()));
			sb.append("]");
