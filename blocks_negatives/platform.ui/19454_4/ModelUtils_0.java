				if( positionInList.startsWith("first") ) {
					index = 0;
				} else if( positionInList.startsWith("index:") ) {
					index = Integer.parseInt(positionInList.substring("index:".length()));	
				} else if( positionInList.startsWith("before:") || positionInList.startsWith("after:") ) {
					String elementId;
					boolean before;
					if( positionInList.startsWith("before:") ) {
						elementId = positionInList.substring("before:".length());
						before = true;
					} else {
						elementId = positionInList.substring("after:".length());
						before = false;
					}
					
					int tmpIndex = -1;
					for( int i = 0; i < list.size(); i++ ) {
						if( elementId.equals(((MApplicationElement)list.get(i)).getElementId()) ) {
							tmpIndex = i;
							break;
						}
					}
					
					if( tmpIndex != -1 ) {
						if( before ) {
							index = tmpIndex;
						} else {
							index = tmpIndex + 1;
						}
					} else {
						System.err.println("Could not find element with Id '"+elementId+"'");
					}
