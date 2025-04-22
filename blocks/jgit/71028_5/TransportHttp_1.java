			} catch (NotSupportedException e) {
				throw e;
			} catch (TransportException e) {
				throw e;
			} catch (IOException e) {
				if (authMethod.getType() == HttpAuthMethod.Type.NONE) {
					if (ignoreTypes == null) {
						ignoreTypes = new HashSet<Type>();
					}

					ignoreTypes.add(authMethod.getType());

					authMethod = HttpAuthMethod.Type.NONE.method(null);
					authAttempts = 1;
				}

				throw new TransportException(uri
