		/*
		 * Bug in GTK, see SWT bug: 62405 Editor drop down performance slow on
		 * Linux-GTK on mouse move. Rather then removing the support altogether
		 * this feature has been worked around for GTK only as we expect that
		 * newer versions of GTK will no longer exhibit this quality and we will
		 * be able to have the desired support running on all platforms. See
		 * remove this code once bug 62405 is fixed for the mainstream GTK
		 * version
		 */
		final int ignoreEventCount = Util.isGtk() ? 4 : 1;
