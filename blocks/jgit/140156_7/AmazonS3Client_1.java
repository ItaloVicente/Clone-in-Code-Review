				break;
				for (String k : s3.list(bucket
					outw.println(k);
				break;
				s3.delete(bucket
				break;
				try (OutputStream os = s3.beginPut(bucket
					final byte[] tmp = new byte[2048];
					int n;
					while ((n = ins.read(tmp)) > 0)
						os.write(tmp
				}
				break;
			default:
				throw die(MessageFormat
						.format(CLIText.get().unsupportedOperation
