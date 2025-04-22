	public static EditList singleton(Edit edit) {
		EditList res = new EditList(1);
		res.add(edit);
		return res;
	}

