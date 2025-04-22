	@Override
	public ArrayList<String> checkListTexts() {
		ArrayList<String> list = new ArrayList<String>(CHECKLIST_SIZE);
		list.add("&1) all widgets are accessible by tabbing.");
		list.add("&2) forwards and backwards tabbing is in a logical order");
		list.add("&3) all the widgets with labels have an appropriate mnemonic.");
		list.add("&4) there are no duplicate mnemonics.");
		list.add("&5) selectable widgets can be selected using the spacebar.");
		return list;
	}
