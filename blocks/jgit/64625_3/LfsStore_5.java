		case S3:
			readAWSKeys();
			checkOptions();
			S3Config config = new S3Config(region.toString()
					storageClass.toString()
					expirationSeconds
			repository = new S3Repository(config);
			break;
