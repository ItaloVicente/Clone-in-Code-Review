				PullResult res = (PullResult) item.getValue();
				if (res.isSuccessful()) {
					return UIText.MultiPullResultDialog_OkStatus;
				} else {
					return UIText.MultiPullResultDialog_FailedStatus;
				}
