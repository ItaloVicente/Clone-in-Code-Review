							int totalWork = 1;
							if (doCheckout)
								totalWork++;
							if (doCreateTag || doCreateBranch)
								totalWork++;
							monitor.beginTask(
									UIText.FetchGerritChangePage_GetChangeTaskName,
									totalWork);

