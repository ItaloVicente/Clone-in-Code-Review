	public static final boolean defaultToIPV4() {
		if(("::ffff:" + IPV4_ADDR).equals(IPV6_ADDR)) {
			return true;
		}
		return false;
	}

