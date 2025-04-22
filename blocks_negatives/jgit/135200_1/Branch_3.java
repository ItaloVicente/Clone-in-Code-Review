			} else if (createForce || branch != null) {
				String newHead = branch;
				String startBranch;
				if (createForce) {
					startBranch = otherBranch;
				} else {
					startBranch = Constants.HEAD;
				}
				Ref startRef = db.findRef(startBranch);
				if (startRef != null) {
					startBranch = startRef.getName();
				} else if (startAt != null) {
					startBranch = startAt.name();
