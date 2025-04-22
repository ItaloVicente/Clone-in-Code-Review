				} finally {
					if(dst != null){
						try {
							dst.close();
						} catch(IOException e){
						}
					}
					if(src != null){
						try {
							src.close();
						} catch(IOException e){
						}
					}
