		 * ActionBarAdvisor to fill the TopTrim-Bar. Both this and the
		 * ActionBarAdvisor fill method will be called after the entire
		 * application model and all its fragments have been build already. This
		 * leads to the effect that all the elements contributed via the
		 * application model would be placed in front of the elements
		 * contributed by the setup() method. (Means all the "Save", "Save All",
		 * and so on, buttons which are normally placed at the beginning of the
		 * trimbar (left) would be moved to the end of it (right).)
