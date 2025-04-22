				break;
			case TAG_TOPIC:
				{
					TopicHandler h = new TopicHandler(atts.getValue(ATT_ID), atts
							.getValue(ATT_HREF));
					h.setParent(ItemHandler.this);
					parser.getXMLReader().setContentHandler(h);
					break;
				}
			default:
				break;
