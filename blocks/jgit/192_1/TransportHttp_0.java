
			String userInfo = u.getUserInfo();
			if (userInfo != null) {
				byte[] bytes = u.getUserInfo().getBytes();
				String encodedUserInfo = Base64.encodeBytes(bytes);
				c.setRequestProperty("Authorization"
						+ encodedUserInfo);
			}

