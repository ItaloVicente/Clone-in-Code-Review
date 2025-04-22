			session = connect(uri
					future -> notifyCloseListeners()
		} catch (IOException e) {
			disconnect(e);
			throw e;
		}
	}
