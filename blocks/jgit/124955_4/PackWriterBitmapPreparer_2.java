				int cardinality = tipBitmap.cardinality();
				seen.or(tipBitmap);

				List<List<BitmapCommit>> chains = new ArrayList<>();

				boolean isActiveBranch = true;
				if (totalWants > excessiveBranchCount && !isRecentCommit(rc)) {
					isActiveBranch = false;
