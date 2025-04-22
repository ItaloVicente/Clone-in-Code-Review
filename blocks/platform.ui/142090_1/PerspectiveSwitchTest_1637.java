	public PerspectiveSwitchTest(String [] ids, int tagging) {
		super("testPerspectiveSwitch:" + ids[0] + "," + ids[1] + ",editor " + ids[2], tagging);
		this.id1 = ids[0];
		this.id2 = ids[1];
		this.activeEditor = ids[2];
	}
