				try {
					revision.parseBody();
				} catch (IOException e) {
					Activator.error("Error parsing body", e); //$NON-NLS-1$
					continue;
				}
