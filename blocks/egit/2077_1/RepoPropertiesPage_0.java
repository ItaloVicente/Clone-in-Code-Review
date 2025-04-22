	public void setUser(String user) {
		user().setText(user);
	}

	public void setPassword(String password) {
		password().setText(password);
	}
	
	public void setStoreInSecureStore(boolean store) {
		if (store)
			storeCheckBox().select();
		else
			storeCheckBox().deselect();
	}
	
