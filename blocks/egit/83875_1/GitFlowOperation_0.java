			MergeResult result = mergeOperation.getResult();
			if (result == null) {
				throw new CoreException(error(format(
						CoreText.GitFlowOperation_unableToMerge, branchName,
						targetBranchName)));
			}

			return result;
