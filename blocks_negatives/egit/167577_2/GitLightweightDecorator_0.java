	 * Our own private cache of current colors and fonts. Do not rely on the
	 * platform's current theme caching them: when a theme switch occurs, the
	 * decorator running asynchronously in the background may get not only wrong
	 * but even disposed values, and somehow it never really worked for the
	 * default font. The main idea behind caching colors and fonts is to do load
	 * them on the UI thread once, and then access them from the decorators
	 * background thread. Otherwise the decorator would need to syncExec(),
	 * which kind of obviates running in the background.
