					if (System.currentTimeMillis()
							- resource.getLocalTimeStamp() > 10000)
						return false;

					if (Team.isIgnoredHint(resource))
						return false;

