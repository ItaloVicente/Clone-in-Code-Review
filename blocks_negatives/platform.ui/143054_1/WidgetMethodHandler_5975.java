					 * Okay. Have a seat. Relax a while. This is going to be a
                     * bumpy ride. If it is an embedded widget, then it *might*
                     * be a Swing widget. At the point where this handler is
					 * executing, the key event is already bound to be
					 * swallowed. If I don't do something, then the key will be
					 * gone for good. So, I will try to forward the event to the
					 * Swing widget. Unfortunately, we can't even count on the
					 * Swing libraries existing, so I need to use reflection
					 * everywhere. And, to top it off, I need to dispatch the
					 * event on the Swing event queue, which means that it will
					 * be carried out asynchronously to the SWT event queue.
