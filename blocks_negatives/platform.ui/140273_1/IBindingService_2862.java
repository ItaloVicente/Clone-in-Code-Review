	 * Gets the formatted string representing the best active binding for a
	 * command. The best binding is the one that would be most appropriate to
	 * show in a menu. Bindings which belong to a child scheme are given
	 * preference over those in a parent scheme. The rest of the calculaton is
	 * based most on various concepts of "length", as well as giving some
	 * modifier keys preference (e.g., <code>Alt</code> is less likely to
	 * appear than <code>Ctrl</code>).
