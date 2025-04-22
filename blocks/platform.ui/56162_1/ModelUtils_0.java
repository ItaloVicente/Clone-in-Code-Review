					switch (posInfo.getPosition()){
					case FIRST:
						index = 0;
						break;

					case INDEX:
						index = posInfo.getPositionReferenceAsInteger();
						break;

					case BEFORE:
					case AFTER:
						int tmpIndex = -1;
						String elementId = posInfo.getPositionReference();

						for( int i = 0; i < list.size(); i++ ) {
							if (elementId.equals(list.get(i).getElementId())) {
								tmpIndex = i;
								break;
							}
						}

						if( tmpIndex != -1 ) {
							if( posInfo.getPosition() == Position.BEFORE ) {
								index = tmpIndex;
							} else {
								index = tmpIndex + 1;
							}
						} else {
							System.err.println("Could not find element with Id '"+elementId+"'");
						}

					case LAST:
					default:
						break;
					}
