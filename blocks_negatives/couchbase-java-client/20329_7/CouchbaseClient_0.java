      deleteOp.set(false, deleteOp.getStatus());
    } catch (TimeoutException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (IllegalArgumentException e) {
      deleteOp.set(false, deleteOp.getStatus());
    } catch (RuntimeException e) {
      deleteOp.set(false, deleteOp.getStatus());
