			if (blobMaxBytes >= 0) {
				pw.setBlobMaxBytes(blobMaxBytes);
				pw.setUseBitmaps(false);
			} else {
				pw.setUseBitmaps(depth == 0 && clientShallowCommits.isEmpty());
			}
