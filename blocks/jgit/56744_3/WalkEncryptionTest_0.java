		static void policySetup(boolean restrictedOn) {
			try {
				java.lang.reflect.Field isRestricted = Class
						.forName("javax.crypto.JceSecurity")
						.getDeclaredField("isRestricted");
				isRestricted.setAccessible(true);
				isRestricted.set(null
			} catch (Throwable e) {
				logger.info(
						"Could not setup JCE security policy restrictions.");
			}
		}

		static void reportPolicy() {
			try {
				java.lang.reflect.Field isRestricted = Class
						.forName("javax.crypto.JceSecurity")
						.getDeclaredField("isRestricted");
				isRestricted.setAccessible(true);
				logger.info("JCE security policy restricted="
						+ isRestricted.get(null));
			} catch (Throwable e) {
				logger.info(
						"Could not report JCE security policy restrictions.");
			}
		}

		static List<Object[]> product(List<String> one
			List<Object[]> result = new ArrayList<Object[]>();
			for (String s1 : one) {
				for (String s2 : two) {
					result.add(new Object[] { s1
				}
			}
			return result;
		}

