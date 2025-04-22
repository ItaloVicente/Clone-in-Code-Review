	private ToolItem getItemFor(ElementAction type) {
		switch (type) {
		case EDIT:
			return itemEdit;
		case FIXUP:
			return itemFixup;
		case PICK:
			return itemPick;
		case REWORD:
			return itemReword;
		case SQUASH:
			return itemSquash;
		case SKIP:
			return itemSkip;
		default:
			return null;
