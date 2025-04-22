			transport = Transport.open(localDb, uri);
			if (credentialsProvider != null)
				transport.setCredentialsProvider(credentialsProvider);
			transport.setTimeout(this.timeout);

			if (pm != null)
				pm.beginTask(CoreText.ListRemoteOperation_title,
						IProgressMonitor.UNKNOWN);
			connection = transport.openFetch();
			remoteRefsMap = connection.getRefsMap();
		} catch (NotSupportedException e) {
			throw new InvocationTargetException(e);
		} catch (TransportException e) {
