			switch (localName) {
			case TAG_BOLD:
				{
					BoldHandler h = new BoldHandler();
					h.setParent(ItemHandler.this);
					parser.getXMLReader().setContentHandler(h);
					break;
				}
			case TAG_ACTION:
				{
					ActionHandler h = new ActionHandler(atts
							.getValue(ATT_PLUGIN_ID), atts.getValue(ATT_CLASS));
					h.setParent(ItemHandler.this);
					parser.getXMLReader().setContentHandler(h);
					break;
				}
			case TAG_PARAGRAPH:
