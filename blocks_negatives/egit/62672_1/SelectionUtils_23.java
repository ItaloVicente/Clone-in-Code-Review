				Repository nextRepo = null;
				if (o instanceof Repository)
					nextRepo = (Repository) o;
				else if (o instanceof PlatformObject)
					nextRepo = CommonUtils.getAdapter(((PlatformObject) o), Repository.class);
				if (nextRepo != null && result != null
						&& !result.equals(nextRepo)) {
