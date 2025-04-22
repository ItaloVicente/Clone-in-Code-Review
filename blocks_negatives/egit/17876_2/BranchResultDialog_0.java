			try {
				if (ObjectId.isId(repository.getFullBranch()))
					showDetachedHeadWarning();
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
