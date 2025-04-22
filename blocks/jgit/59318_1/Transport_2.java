			if (proto.canHandle(uri
				Transport tn = proto.open(uri
				tn.prePush = Hooks.prePush(local
				tn.prePush.setRemoteLocation(uri.toString());
				tn.prePush.setRemoteName(remoteName);
				return tn;
			}
