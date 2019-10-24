package app.storytel.candidate.com.listeners;

/**
 * interface to handle response callback
 */
public interface ResultListener {
    void onError(String err);
    void onSuccess();
}