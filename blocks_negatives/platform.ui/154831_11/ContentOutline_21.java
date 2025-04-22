			return key.cast(new IContributedContentsView() {
				@Override
				public IWorkbenchPart getContributingPart() {
					return getContributingEditor();
				}
			});
