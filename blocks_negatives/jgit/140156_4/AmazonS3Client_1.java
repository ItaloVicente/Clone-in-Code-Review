				outs.flush();
			}

			for (String k : s3.list(bucket, key))
				outw.println(k);

			s3.delete(bucket, key);

			try (OutputStream os = s3.beginPut(bucket, key, null, null)) {
				final byte[] tmp = new byte[2048];
				int n;
				while ((n = ins.read(tmp)) > 0)
					os.write(tmp, 0, n);
