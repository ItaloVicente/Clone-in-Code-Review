							final Repository db) throws IOException
							ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = uploadPackFactory.create(dc
						InputStream in = dc.getInputStream();
						OutputStream out = dc.getOutputStream();
						up.upload(in
