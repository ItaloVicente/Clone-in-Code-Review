        } catch (ConnectionException ex) {
            doingResubscribe=new AtomicBoolean(true);
            if(waitTime == maxWaitTime){
              doingResubscribe=new AtomicBoolean(false);
            }
         } catch (Exception ex) {
