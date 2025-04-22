		/*
		 * FileNames ".aaa.txt" and "zzz.txt" seem to be sufficient to get the
		 * desired checkout order before and after ".lfsconfig", at least in a
		 * number of manual tries. Since the files to checkout are contained in
		 * a set (see DirCacheCheckout::doCheckout) the order cannot be
		 * guaranteed.
		 */

