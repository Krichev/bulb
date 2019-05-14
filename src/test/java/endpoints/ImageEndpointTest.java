package endpoints;

import org.junit.jupiter.api.*;

import org.mockito.Mockito;

import javax.websocket.Session;


import java.util.List;

import static org.assertj.core.api.Java6Assertions.*;
import static org.mockito.Mockito.*;


@DisplayName("ImageEndpoint tiny test")
public class ImageEndpointTest {
    private ImageEndpoint imageEndpoint;


    @BeforeEach
    void SetUp() {
        imageEndpoint = new ImageEndpoint();
    }

    @Test
    @DisplayName("invoke onmessage with invalid param") //of course method wiil take session from list, so
    void testOk() {
        Session session = mock(Session.class);
        imageEndpoint.OnMessage(session, "ABBA");
        when(session.getBasicRemote()).thenThrow(new IllegalArgumentException());

    }


}
