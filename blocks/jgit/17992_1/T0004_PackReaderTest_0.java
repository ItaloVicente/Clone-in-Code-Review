		PackFile pr = null;
		for (PackFile p : db.getObjectDatabase().getPacks()) {
			if (PACK_NAME.equals(p.getPackName())) {
				pr = p;
				break;
			}
		}
		assertNotNull("have pack-" + PACK_NAME

