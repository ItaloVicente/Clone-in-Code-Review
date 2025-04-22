			}
		} else if (refName.equals(Constants.HEAD)) {
			decoration.addOverlay(UIIcons.OVR_CHECKEDOUT, IDecoration.TOP_LEFT);
			return;
		} else {
			String leafname = leaf.getName();
			if (leafname.startsWith(Constants.R_REFS)
					&& leafname.equals(branchName)) {
				decoration.addOverlay(UIIcons.OVR_CHECKEDOUT,
						IDecoration.TOP_LEFT);
