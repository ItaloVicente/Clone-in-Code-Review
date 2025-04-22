			private String local(String url
				if (!toLocal) {
					return url;
				}
				try {
					URI u = new URI(url);
					String fragment = u.getRawFragment();
					if (fragment != null) {
						return u.getRawPath() + '#' + fragment;
					} else {
						return u.getRawPath();
					}
				} catch (URISyntaxException e) {
					return url;
				}
			}

