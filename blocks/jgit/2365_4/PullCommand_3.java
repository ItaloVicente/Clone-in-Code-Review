			if (isRemote) {
				Ref r = null;
				if (fetchRes != null) {
					r = fetchRes.getAdvertisedRef(remoteBranchName);
					if (r == null)
						r = fetchRes.getAdvertisedRef(Constants.R_HEADS
								+ remoteBranchName);
				}
