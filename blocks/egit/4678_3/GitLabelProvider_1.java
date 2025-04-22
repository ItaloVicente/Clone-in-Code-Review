
			BranchTrackingStatus trackingStatus = BranchTrackingStatus.of(repository, branch);
			if (trackingStatus != null && (trackingStatus.getAheadCount() != 0 || trackingStatus.getBehindCount() != 0)) {
				String formattedTrackingStatus = formatBranchTrackingStatus(trackingStatus);
				string.append(' ');
				string.append(formattedTrackingStatus, StyledString.DECORATIONS_STYLER);
			}
