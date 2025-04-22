
		} catch (IOException err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
			throw err;
		} catch (RuntimeException err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
			throw err;
		} catch (Error err) {
			reportErrorDuringNegotiate(JGitText.get().internalServerError);
