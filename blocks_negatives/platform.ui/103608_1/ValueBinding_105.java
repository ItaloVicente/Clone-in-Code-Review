					status = updateValueStrategy
							.validateAfterConvert(convertedValue);
					if (!mergeStatus(multiStatus, status))
						return;
					if (policy == UpdateValueStrategy.POLICY_CONVERT
							&& !explicit)
						return;
