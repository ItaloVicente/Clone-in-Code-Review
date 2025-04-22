				ListDiff diff = event.diff;
				if (computeNextDiff) {
					diff = Diffs.computeListDiff(wrappedList, target);
					computeNextDiff = false;
				}
				applyDiff(diff, wrappedList);
				fireListChange(diff);
