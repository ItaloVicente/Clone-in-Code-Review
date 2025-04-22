							final Repository db) throws IOException {
						final UploadPack rp = new UploadPack(db);
						final InputStream in = dc.getInputStream();
						rp.setTimeout(Daemon.this.getTimeout());
						rp.setPackConfig(Daemon.this.packConfig);
						rp.upload(in, dc.getOutputStream(), null);
