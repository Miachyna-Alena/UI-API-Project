package utils;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.Unirest;

public class APIUtils {
    public static HttpRequestWithBody post(String route) {
        return Unirest.post(route);
    }
}
