			rawIn = input;
			rawOut = output;
			msgOut = messages;

			if (timeout > 0) {
				final Thread caller = Thread.currentThread();
				timer = new InterruptTimer(caller.getName() + "-Timer");
				timeoutIn = new TimeoutInputStream(rawIn, timer);
				TimeoutOutputStream o = new TimeoutOutputStream(rawOut, timer);
				timeoutIn.setTimeout(timeout * 1000);
				o.setTimeout(timeout * 1000);
				rawIn = timeoutIn;
				rawOut = o;
			}

			pckIn = new PacketLineIn(rawIn);
			pckOut = new PacketLineOut(rawOut);
			pckOut.setFlushOnEnd(false);

			commands = new ArrayList<ReceiveCommand>();

