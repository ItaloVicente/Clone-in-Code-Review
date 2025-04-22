	public void setVbucketlist(int[] vbs) {
		byte[] vblist = new byte[(vbs.length + 1) * VBUCKET_LIST_FIELD_LENGTH];
		for (int i = 0; i < vbs.length + 1; i++) {
			if (i == 0) {
				Util.valueToFieldOffest(vblist, 0, VBUCKET_LIST_FIELD_LENGTH, (long) vbs.length);
			} else if (vbs[i-1] < NUM_VBUCKETS && vbs[i-1] >= 0) {
				Util.valueToFieldOffest(vblist, (i * VBUCKET_LIST_FIELD_LENGTH), VBUCKET_LIST_FIELD_LENGTH, (long) vbs[i-1]);
			} else {
				getLogger().error("vBucket ignored " + vbs[i-1] + "is not a valid vBucket number");
			}
		}
		vbucketlist = vblist;
		encode();
