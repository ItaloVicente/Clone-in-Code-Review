			BranchTrackingStatus trackingStatus = BranchTrackingStatus
					.of(repository, branch);
			if (trackingStatus != null && (trackingStatus.getAheadCount() != 0
					|| trackingStatus.getBehindCount() != 0)) {
				String formattedTrackingStatus = GitLabels
						.formatBranchTrackingStatus(trackingStatus);
				suffix.append(' ').append(formattedTrackingStatus);
