	public static List<TapFlag> getFlags(short f) {
		List<TapFlag> flags = new LinkedList<TapFlag>();
		if ((f & TapFlag.BACKFILL.flag) == 1) {
			flags.add(TapFlag.BACKFILL);
		}
		if ((f & TapFlag.DUMP.flag) == 1) {
			flags.add(TapFlag.DUMP);
		}
		if ((f & TapFlag.LIST_VBUCKETS.flag) == 1) {
			flags.add(TapFlag.LIST_VBUCKETS);
		}
		if ((f & TapFlag.TAKEOVER_VBUCKETS.flag) == 1) {
			flags.add(TapFlag.TAKEOVER_VBUCKETS);
		}
		if ((f & TapFlag.SUPPORT_ACK.flag) == 1) {
			flags.add(TapFlag.SUPPORT_ACK);
		}
		if ((f & TapFlag.KEYS_ONLY.flag) == 1) {
			flags.add(TapFlag.KEYS_ONLY);
		}
		if ((f & TapFlag.CHECKPOINT.flag) == 1) {
			flags.add(TapFlag.CHECKPOINT);
