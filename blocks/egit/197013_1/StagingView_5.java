	private IAction compareWithEachOther(StagingEntry left,
			StagingEntry right) {
		if (left.isStaged()) {
			return new Action(UIText.StagingView_CompareWithEachOtherLabel,
					UIIcons.ELCL16_COMPARE_VIEW) {

				@Override
				public void run() {
					CompareUtils.compareBetween(currentRepository,
							left.getPath(), right.getPath(),
							GitFileRevision.INDEX, GitFileRevision.INDEX,
							StagingView.this.getViewSite().getPage());
				}
			};
		}
		return new Action(UIText.StagingView_CompareWithEachOtherLabel,
				UIIcons.ELCL16_COMPARE_VIEW) {

			@Override
			public void run() {
				CompareUtils.compareFiles(left.getFile(), right.getFile(),
						left.getLocation().toFile(),
						right.getLocation().toFile(), StagingView.this);
			}
		};
	}

