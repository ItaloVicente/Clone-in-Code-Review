			FetchCommand fetch = new FetchCommand(repo);
			fetch.setRemote(remote);
			if (monitor != null)
				fetch.setProgressMonitor(monitor);
			fetch.setTimeout(this.timeout);

			fetchRes = fetch.call();
		} else {
			remoteUri = "local repository";
			fetchRes = null;
		}
