				String qName, Attributes atts) throws SAXException {
			textStart = offset;
			if (localName.equals(TAG_BOLD)) {
				BoldHandler h = new BoldHandler();
				h.setParent(ItemHandler.this);
				parser.getXMLReader().setContentHandler(h);
			} else if (localName.equals(TAG_ACTION)) {
				ActionHandler h = new ActionHandler(atts
						.getValue(ATT_PLUGIN_ID), atts.getValue(ATT_CLASS));
				h.setParent(ItemHandler.this);
				parser.getXMLReader().setContentHandler(h);
			} else if (localName.equals(TAG_PARAGRAPH)) {
				wrapStart = textStart;
			} else if (localName.equals(TAG_TOPIC)) {
				TopicHandler h = new TopicHandler(atts.getValue(ATT_ID), atts
						.getValue(ATT_HREF));
				h.setParent(ItemHandler.this);
				parser.getXMLReader().setContentHandler(h);
			}
		}

		@Override
