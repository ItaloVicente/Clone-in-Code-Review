				SyncData syncData = repoMapping.get(element);
				if (syncData == null)
					return Integer.valueOf(0);

				String branch = syncData.srcRev;
				CCombo combo = (CCombo) srcBranchesEditor.getControl();

				return Integer.valueOf(branch == null ? 0 : combo
						.indexOf(branch));
