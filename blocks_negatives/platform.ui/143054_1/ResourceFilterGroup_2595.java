					DataOutputStream writeOut = new DataOutputStream(out);
					writeOut.writeInt(myTypes.length);
					for (FilterCopy myType : myTypes)
						writeOut.writeInt(myType.getSerialNumber());
					byte[] buffer = out.toByteArray();
					writeOut.close();
