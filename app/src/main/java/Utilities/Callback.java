package Utilities;

/**
 * Created by rivareus24 on 30/03/2018.
 */

public interface Callback<T> {
    void onSuccess(T data);

    void onFailure(Error error, String message);
}
