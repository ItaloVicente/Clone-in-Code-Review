		/**
		 * Iterate over starters bitmaps and remove targets as they become
		 * reachable.
		 *
		 * Building the total starters bitmap has the same cost (iterating over
		 * all starters adding the bitmaps) and this gives us the chance to
		 * shorcut the loop.
		 *
		 * This is based on the assuption that most of the starters will have
		 * the reachability bitmap precalculated. If many require a walk, the
		 * walk.reset() could start to take too much time.
		 */
