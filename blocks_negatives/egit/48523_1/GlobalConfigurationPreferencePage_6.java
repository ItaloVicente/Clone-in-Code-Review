			@Override
			protected void setChangeSystemPrefix(String prefix) throws IOException {
				FS.DETECTED.setGitPrefix(new File(prefix));
				sysConfig = SystemReader.getInstance().openSystemConfig(null,
						FS.DETECTED);
				setConfig(sysConfig);
			}

