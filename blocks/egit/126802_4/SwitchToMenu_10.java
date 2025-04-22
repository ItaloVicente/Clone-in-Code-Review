
				for (Entry<Repository, String> entry : repoToFullNameMap
						.entrySet()) {
					BranchOperationUI.checkout(entry.getKey(), entry.getValue())
							.start();
				}
