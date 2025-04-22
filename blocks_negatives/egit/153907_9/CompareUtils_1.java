			FileElement local = null;
			if (leftRevision != null) {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL,
						repository.getWorkTree(), leftRevision.getContents());
			} else {
				local = new FileElement(mergedFilePath, FileElement.Type.LOCAL, repository.getWorkTree());
