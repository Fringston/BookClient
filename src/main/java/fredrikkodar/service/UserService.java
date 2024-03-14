package fredrikkodar.service;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class UserService {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

}
