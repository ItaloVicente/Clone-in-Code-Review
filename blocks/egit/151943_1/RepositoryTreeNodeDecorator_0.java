				if (numberOfBranches <= 1) {
					String thisBranch = DecoratableResourceHelper
							.getShortBranch(repo);
					if (!thisBranch.equals(singleBranch)) {
						numberOfBranches++;
					}
					if (singleBranch == null) {
						singleBranch = thisBranch;
					}
				}
				if (markGroupDirty && numberOfBranches > 1) {
