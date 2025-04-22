			if (locator instanceof VBucketNodeLocator) {
				int vbucketIndex = ((VBucketNodeLocator) locator).getVBucketIndex(key);
				if (o instanceof VBucketAware) {
					((VBucketAware) o).setVBucket(vbucketIndex);
				}
			}
