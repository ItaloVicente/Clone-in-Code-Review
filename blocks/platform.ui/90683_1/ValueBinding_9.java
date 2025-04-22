				status = updateValueStrategy
						.validateBeforeSet(convertedValue);
				if (!mergeStatus(multiStatus, status))
					return;
				if (validateOnly)
					return;
