		GC_TXN(1),

		/**
		 * The pack was created by compacting multiple packs together.
		 * <p>
		 * Packs created by compacting multiple packs together aren't nearly as
		 * efficient as a fully garbage collected repository, but may save disk
		 * space by reducing redundant copies of base objects.
		 *
		 * @see DfsPackCompactor
		 */
		COMPACT(1),
