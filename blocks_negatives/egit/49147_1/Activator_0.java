
				if (projects.size() > 0) {
					ConnectProviderOperation op = new ConnectProviderOperation(
							projects);
					JobUtil.scheduleUserJob(op,
							CoreText.Activator_AutoShareJobName,
							JobFamilies.AUTO_SHARE);
