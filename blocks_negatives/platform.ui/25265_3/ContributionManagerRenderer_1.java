	@SuppressWarnings("unchecked")
	public void addRecord(ModelEl modelEl, ContrRec rec) {
		List<ContrRec> tmp = (List<ContrRec>) modelEl.getTransientData().get(
				CONTIBUTION_RECORD_LIST_FOR_MODEL);
		if (tmp == null) {
			tmp = new ArrayList<ContrRec>();
			modelEl.getTransientData().put(CONTIBUTION_RECORD_LIST_FOR_MODEL,
					tmp);
		}
		tmp.add(rec);
