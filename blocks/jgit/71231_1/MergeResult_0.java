

	@SuppressWarnings("nls")
	public String toStringExt() {
		StringBuffer b=new StringBuffer();
		b.append("{mergedCommits:[");
		String sep="";
		for(ObjectId mc:mergedCommits) {
			b.append(sep).append(name(mc));
			sep="
		}
		b.append("]
		b.append("
		b.append("
		b.append("
		b.append("
				(mergeStrategy == null) ? "null" : mergeStrategy.getName());
		b.append("
		if (failingPaths!=null) {
			sep="";
			for(String fp:failingPaths.keySet()) {
				b.append(sep).append(fp).append("->").append(failingPaths.get(fp));
				sep="
			}
		}
		b.append("]
		if (checkoutConflicts!=null) {
			sep="";
			for(String co:checkoutConflicts) {
				b.append(sep).append(co);
				sep="
			}
		}
		b.append("]
		if (conflicts!=null) {
			sep="";
			for(String co:conflicts.keySet()) {
				b.append(sep).append(co).append("->[");
				int r[][]=conflicts.get(co);
				if (r!=null) {
					String sep2="";
					for(int i=0; i<r.length; i++) {
						if (r[i]!=null) {
							for(int j=0; j<r[i].length; j++) {
								b.append(sep2).append("[").append(i).append("][").append(j).append("]:").append(r[i][j]);
								sep2="
							}
						}
					}
				}
				b.append("]");
				sep="
			}
		}
		b.append("]}");
		return (b.toString());
	}

	private String name(ObjectId id) {
		return (id == null) ? "null" : id.getName();
	}
