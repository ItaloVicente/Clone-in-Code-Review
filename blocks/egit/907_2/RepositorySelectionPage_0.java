					int index = text.indexOf(' ');
					if(index > 0)
						text = text.substring(0,index);
					if(Transport.canHandleProtocol(new URIish(text), FS.DETECTED)) {
						if(!text.startsWith("http") || text.contains(".git")) //$NON-NLS-1$ //$NON-NLS-2$
							preset = text;
					}
