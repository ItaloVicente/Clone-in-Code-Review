				MapDiff diff = event.diff;
				if (computeNextDiff) {
					diff = Diffs.computeMapDiff(wrappedMap, target);
					computeNextDiff = false;
				}
				applyDiff(diff, wrappedMap);
				fireMapChange(diff);
