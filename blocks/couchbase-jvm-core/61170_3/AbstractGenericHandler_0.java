            if (moveResponseOut) {
                Scheduler scheduler = env().scheduler();
                if (scheduler instanceof CoreScheduler) {
                    scheduleDirect((CoreScheduler) scheduler, response, observable);
                } else {
                    scheduleWorker(scheduler, response, observable);
                }
