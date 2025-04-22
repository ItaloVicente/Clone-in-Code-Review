				SetDiff diff = event.diff;
				if (computeNextDiff) {
					diff = Diffs.computeSetDiff(wrappedSet, target);
					computeNextDiff = false;
				}
				applyDiff(diff, wrappedSet);
				fireSetChange(diff);
