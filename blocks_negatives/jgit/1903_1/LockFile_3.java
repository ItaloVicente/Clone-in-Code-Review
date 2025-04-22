			if (fLck != null) {
				try {
					fLck.release();
				} catch (IOException ioe) {
				}
				fLck = null;
			}
