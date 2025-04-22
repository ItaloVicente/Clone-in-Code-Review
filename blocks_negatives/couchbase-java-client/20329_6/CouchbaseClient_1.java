      setOp.set(false, setOp.getStatus());
    } catch (TimeoutException e) {
      setOp.set(false, setOp.getStatus());
    } catch (IllegalArgumentException e) {
      setOp.set(false, setOp.getStatus());
    } catch (RuntimeException e) {
      setOp.set(false, setOp.getStatus());
