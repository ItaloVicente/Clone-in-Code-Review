			File attributesFile;
				attributesFile = fs.resolve(fs.userHome(),
						path.substring(2));
			} else {
				attributesFile = fs.resolve(null, path);
			}
