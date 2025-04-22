				try {
					UserPasswordCredentials credentials = store
							.getCredentials(uri);
					if (credentials != null) {
						String password = credentials.getPassword();
						if (password != null) {
							char[] pass = password.toCharArray();
							state.setPassword(pass);
							return pass;
