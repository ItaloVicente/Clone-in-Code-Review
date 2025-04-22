			try (OutputStream os = s3.beginPut(bucket
				final byte[] tmp = new byte[2048];
				int n;
				while ((n = ins.read(tmp)) > 0)
					os.write(tmp
			}
