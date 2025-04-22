						new Transfer[] { ResourceTransfer.getInstance(),
								FileTransfer.getInstance(),
								TextTransfer.getInstance() });
			} else {
				clipboard.setContents(new Object[] { resources, names },
						new Transfer[] { ResourceTransfer.getInstance(),
								TextTransfer.getInstance() });
			}
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
